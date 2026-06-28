package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.FichaClinicaDto;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FichaClinicaServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FichaClinicaService fichaClinicaService;

    @Value("${gateway.url}")
    private String GATEWAY_URL;
    private static final String TOKEN = "test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(fichaClinicaService, "gatewayUrl", GATEWAY_URL);
    }

    private FichaClinicaDto crearFicha() {
        FichaClinicaDto dto = new FichaClinicaDto();
        dto.setId(1L);
        dto.setNombrePaciente("Juan Perez");
        dto.setRutPaciente("12345678-9");
        dto.setEdad(35);
        dto.setDiagnostico("Hipertensión");
        dto.setGenero("M");
        return dto;
    }

    @Test
    void listar_exitoso_debeRetornarLista() {
        FichaClinicaDto[] array = { crearFicha(), crearFicha() };

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/fichas"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(FichaClinicaDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        List<FichaClinicaDto> result = fichaClinicaService.listar(TOKEN);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan Perez", result.get(0).getNombrePaciente());
    }

    @Test
    void guardar_exitoso_debeRetornarFichaCreada() {
        FichaClinicaDto ficha = crearFicha();

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/fichas"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(FichaClinicaDto.class)))
                .thenReturn(ResponseEntity.ok(ficha));

        FichaClinicaDto result = fichaClinicaService.guardar(TOKEN, ficha);

        assertNotNull(result);
        assertEquals("12345678-9", result.getRutPaciente());
        assertEquals("Hipertensión", result.getDiagnostico());
    }

    @Test
    void guardar_conCamposNulos_debeRetornarRespuestaDelServidor() {
        FichaClinicaDto ficha = new FichaClinicaDto();
        FichaClinicaDto saved = new FichaClinicaDto();
        saved.setId(10L);

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/fichas"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(FichaClinicaDto.class)))
                .thenReturn(ResponseEntity.ok(saved));

        FichaClinicaDto result = fichaClinicaService.guardar(TOKEN, ficha);

        assertEquals(10L, result.getId());
    }

    @Test
    void listar_debeUsarToken() {
        FichaClinicaDto[] array = {};

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(FichaClinicaDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        fichaClinicaService.listar(TOKEN);

        verify(restTemplate, times(1)).exchange(
                eq(GATEWAY_URL + "/fichas"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(FichaClinicaDto[].class));
    }
}
