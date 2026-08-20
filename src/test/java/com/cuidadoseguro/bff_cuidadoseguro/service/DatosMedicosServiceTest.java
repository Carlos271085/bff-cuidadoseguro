package com.cuidadoseguro.bff_cuidadoseguro.service;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatosMedicosServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DatosMedicosService service;

    @Value("${datosmedicos.url}")
    private String DATOS_MEDICOS_URL;
    private static final String TOKEN = "test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "datosMedicosUrl", DATOS_MEDICOS_URL);
    }

    @Test
    void obtenerSignosVitales_debeRetornarDatos() {
        // El servicio hace dos llamadas: exchange a /controles y getForObject a /signos-vitales
        when(restTemplate.exchange(
                eq(DATOS_MEDICOS_URL + "/controles"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
                .thenReturn(ResponseEntity.ok("[{\"presion\":\"120/80\"}]"));

        when(restTemplate.getForObject(
                eq(DATOS_MEDICOS_URL + "/signos-vitales"),
                eq(String.class)))
                .thenReturn("[{\"presion\":\"120/80\"}]");

        String result = service.obtenerSignosVitales(TOKEN);

        assertNotNull(result);
        assertTrue(result.contains("presion"));
    }
}
