package com.cuidadoseguro.bff_cuidadoseguro.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

/**
 * Sin este handler, cualquier fallo de los RestTemplate del BFF hacia el
 * gateway/otros microservicios (401/403/404/409/500/503, timeouts, DNS,
 * conexión rechazada) se escapaba como excepción cruda hasta el manejador
 * por defecto de WebFlux (AbstractErrorWebExceptionHandler), que siempre
 * responde el mismo 500 genérico sin body útil:
 *
 *   {"timestamp":"...","path":"...","status":500,"error":"Internal Server Error"}
 *
 * Esto pasaba incluso cuando el servicio downstream (ej. auth-service) ya
 * devolvía un mensaje claro y un status code correcto (401, 503, etc.) —
 * el BFF lo tiraba a la basura.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * El downstream (a través del gateway) respondió con un status de error
     * HTTP (4xx/5xx). RestTemplate lo convierte en esta excepción.
     * Reenviamos el mismo status code y el mismo body tal cual, porque los
     * servicios downstream (auth-service, datos-medicos-service, etc.) ya
     * arman su propio JSON de error legible.
     */
    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<byte[]> handleDownstreamHttpError(HttpStatusCodeException ex) {

        log.error("Downstream respondió {} — {}", ex.getStatusCode(), ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        MediaType contentType = ex.getResponseHeaders() != null
                ? ex.getResponseHeaders().getContentType()
                : null;
        headers.setContentType(contentType != null ? contentType : MediaType.APPLICATION_JSON);

        return ResponseEntity
                .status(ex.getStatusCode())
                .headers(headers)
                .body(ex.getResponseBodyAsByteArray());
    }

    /**
     * Fallo de red real hacia el downstream: DNS no resuelve, conexión
     * rechazada, timeout, servicio inalcanzable. No hay body de error que
     * reenviar (nunca llegó respuesta), así que armamos uno propio con 503.
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleDownstreamUnreachable(ResourceAccessException ex) {

        log.error("No se pudo alcanzar un servicio downstream: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"success\":false,\"message\":\"Un servicio dependiente no está disponible en este momento. Intente más tarde.\",\"errorCode\":\"DOWNSTREAM_UNREACHABLE\"}");
    }

    /**
     * Último recurso: cualquier otra excepción no anticipada. Se registra
     * completa en el log para poder diagnosticarla, y se devuelve un 500
     * con mensaje genérico pero legible (en vez del whitelabel de WebFlux).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpected(Exception ex) {

        log.error("Error interno no controlado en el BFF", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"success\":false,\"message\":\"Error interno del servidor\",\"errorCode\":\"INTERNAL_ERROR\"}");
    }
}