package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.AntropometriaDto;
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
class AntropometriaServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AntropometriaService antropometriaService;

    
    @Value("${datosmedicos.url}")
    private String DATOS_MEDICOS_URL;
    private static final String TOKEN = "test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(antropometriaService, "datosMedicosUrl", DATOS_MEDICOS_URL);
    }

    private AntropometriaDto crearDto() {
        AntropometriaDto dto = new AntropometriaDto();
        dto.setId(1L);
        dto.setPeso(70.5);
        dto.setAltura(1.75);
        dto.setFechaRegistro("2024-01-15");
        dto.setFichaId(1L);
        dto.setProfesional("Dr. Test");
        return dto;
    }

    @Test
    void guardar_exitoso_debeRetornarDto() {
        Long fichaId = 1L;
        AntropometriaDto dto = crearDto();

        when(restTemplate.exchange(
                eq(DATOS_MEDICOS_URL + "/antropometrias/" + fichaId),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(AntropometriaDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        AntropometriaDto result = antropometriaService.guardar(fichaId, dto, TOKEN);

        assertNotNull(result);
        assertEquals(70.5, result.getPeso());
        assertEquals(1.75, result.getAltura());
    }

    @Test
    void listar_exitoso_debeRetornarLista() {
        Long fichaId = 1L;
        AntropometriaDto[] array = { crearDto(), crearDto() };

        when(restTemplate.exchange(
                eq(DATOS_MEDICOS_URL + "/antropometrias/" + fichaId),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(AntropometriaDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        List<AntropometriaDto> result = antropometriaService.listar(fichaId, TOKEN);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void guardar_conPesoYAltura_debeMapearCorrectamente() {
        Long fichaId = 2L;
        AntropometriaDto dto = new AntropometriaDto();
        dto.setPeso(80.0);
        dto.setAltura(1.80);
        dto.setProfesional("Dra. García");

        AntropometriaDto saved = new AntropometriaDto();
        saved.setId(5L);
        saved.setPeso(80.0);
        saved.setAltura(1.80);

        when(restTemplate.exchange(
                eq(DATOS_MEDICOS_URL + "/antropometrias/" + fichaId),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(AntropometriaDto.class)))
                .thenReturn(ResponseEntity.ok(saved));

        AntropometriaDto result = antropometriaService.guardar(fichaId, dto, TOKEN);

        assertEquals(5L, result.getId());
        assertEquals(80.0, result.getPeso());
    }
}
