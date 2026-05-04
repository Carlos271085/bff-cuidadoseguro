package com.cuidadoseguro.bff_cuidadoseguro.service;

// Importa la anotación Service de Spring
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Importa RestTemplate para consumir APIs externas
import org.springframework.web.client.RestTemplate;

// Marca esta clase como un servicio de Spring
@Service
public class DatosMedicosService {

    // Objeto que permitirá realizar peticiones HTTP
    private final RestTemplate restTemplate;

    // Obtiene la URL del API Gateway desde application.properties
    @Value("${gateway.url}")
    private String gatewayUrl;

    // Constructor para inyectar RestTemplate
    public DatosMedicosService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para obtener los signos vitales
    public String obtenerSignosVitales() {

        // Construye la URL completa del endpoint
        String url = gatewayUrl + "/signos-vitales";

        // Realiza una petición GET al API Gateway
        // y retorna la respuesta como String
        return restTemplate.getForObject(url, String.class);
    }
}