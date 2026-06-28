package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.SignosVitalesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignosVitalesServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SignosVitalesService signosVitalesService;

    @Value("${gateway.url}")
    private String GATEWAY_URL;
    private static final String TOKEN = "test-token";
    private static final String BASE_PATH = "/signos-vitales";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(signosVitalesService, "gatewayUrl", GATEWAY_URL);
    }

    private SignosVitalesDto crearDto() {
        return new SignosVitalesDto(1L, "120/80", 72, 36.5, 98, "Enf. López",
                LocalDateTime.now(), null);
    }

    @Test
    void listarTodos_debeRetornarLista() {
        SignosVitalesDto[] array = { crearDto(), crearDto() };

        when(restTemplate.exchange(
                eq(GATEWAY_URL + BASE_PATH),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SignosVitalesDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        List<SignosVitalesDto> result = signosVitalesService.listarTodos(TOKEN);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void listarTodos_respuestaNull_debeRetornarListaVacia() {
        when(restTemplate.exchange(
                eq(GATEWAY_URL + BASE_PATH),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SignosVitalesDto[].class)))
                .thenReturn(ResponseEntity.ok(null));

        List<SignosVitalesDto> result = signosVitalesService.listarTodos(TOKEN);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void guardar_exitoso_debeRetornarDto() {
        Long fichaId = 1L;
        SignosVitalesDto dto = crearDto();

        when(restTemplate.exchange(
                eq(GATEWAY_URL + BASE_PATH + "/" + fichaId),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(SignosVitalesDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        SignosVitalesDto result = signosVitalesService.guardar(TOKEN, fichaId, dto);

        assertNotNull(result);
        assertEquals("120/80", result.getPresion());
        assertEquals(72, result.getFrecuencia());
    }

    @Test
    void buscarPorId_debeRetornarDto() {
        Long id = 1L;
        SignosVitalesDto dto = crearDto();

        when(restTemplate.exchange(
                eq(GATEWAY_URL + BASE_PATH + "/" + id),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SignosVitalesDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        SignosVitalesDto result = signosVitalesService.buscarPorId(TOKEN, id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void eliminar_exitoso_noDebeLanzarExcepcion() {
        Long id = 1L;

        when(restTemplate.exchange(
                eq(GATEWAY_URL + BASE_PATH + "/" + id),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Void.class)))
                .thenReturn(ResponseEntity.noContent().build());

        assertDoesNotThrow(() -> signosVitalesService.eliminar(TOKEN, id));
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.DELETE), any(), eq(Void.class));
    }

    @Test
    void listarPorFichaConToken_debeRetornarLista() {
        Long fichaId = 1L;
        SignosVitalesDto[] array = { crearDto() };

        when(restTemplate.exchange(
                eq(GATEWAY_URL + BASE_PATH + "/ficha/" + fichaId),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SignosVitalesDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        List<SignosVitalesDto> result = signosVitalesService.listarPorFicha(TOKEN, fichaId);

        assertEquals(1, result.size());
    }

    @Test
    void listarPorFichaConToken_respuestaNull_debeRetornarListaVacia() {
        Long fichaId = 2L;

        when(restTemplate.exchange(
                eq(GATEWAY_URL + BASE_PATH + "/ficha/" + fichaId),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SignosVitalesDto[].class)))
                .thenReturn(ResponseEntity.ok(null));

        List<SignosVitalesDto> result = signosVitalesService.listarPorFicha(TOKEN, fichaId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
