package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.ExamenClinicoDto;
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
class ExamenClinicoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExamenClinicoService service;

    @Value("${datosmedicos.url}")
    private String DATOS_MEDICOS_URL;
    private static final String TOKEN = "test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "datosMedicosUrl", DATOS_MEDICOS_URL);
    }

    private ExamenClinicoDto dto() {
        ExamenClinicoDto dto = new ExamenClinicoDto();
        dto.setId(1L);
        dto.setNombre("Hemograma");
        dto.setEstado("Pendiente");
        return dto;
    }

    @Test
    void listarTodos_debeRetornarLista() {
        ExamenClinicoDto[] array = { dto() };
        when(restTemplate.exchange(eq(DATOS_MEDICOS_URL + "/examenes"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(ExamenClinicoDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        List<ExamenClinicoDto> result = service.listarTodos(TOKEN);

        assertEquals(1, result.size());
        assertEquals("Hemograma", result.get(0).getNombre());
    }

    @Test
    void guardar_debeRetornarDto() {
        ExamenClinicoDto dto = dto();
        when(restTemplate.exchange(eq(DATOS_MEDICOS_URL + "/examenes"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(ExamenClinicoDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        ExamenClinicoDto result = service.guardar(TOKEN, dto);

        assertNotNull(result);
        assertEquals("Pendiente", result.getEstado());
    }

    @Test
    void actualizar_debeRetornarDtoActualizado() {
        ExamenClinicoDto dto = dto();
        dto.setEstado("Completado");
        when(restTemplate.exchange(eq(DATOS_MEDICOS_URL + "/examenes/1"), eq(HttpMethod.PUT),
                any(HttpEntity.class), eq(ExamenClinicoDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        ExamenClinicoDto result = service.actualizar(TOKEN, 1L, dto);

        assertEquals("Completado", result.getEstado());
    }

    @Test
    void actualizar_conError_debePropagar() {
        ExamenClinicoDto dto = dto();
        when(restTemplate.exchange(eq(DATOS_MEDICOS_URL + "/examenes/1"), eq(HttpMethod.PUT),
                any(HttpEntity.class), eq(ExamenClinicoDto.class)))
                .thenThrow(new RuntimeException("Error de red"));

        assertThrows(RuntimeException.class, () -> service.actualizar(TOKEN, 1L, dto));
    }

    @Test
    void buscarPorId_debeRetornarDto() {
        when(restTemplate.exchange(eq(DATOS_MEDICOS_URL + "/examenes/1"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(ExamenClinicoDto.class)))
                .thenReturn(ResponseEntity.ok(dto()));

        ExamenClinicoDto result = service.buscarPorId(TOKEN, 1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void eliminar_debeEjecutarseSinError() {
        when(restTemplate.exchange(eq(DATOS_MEDICOS_URL + "/examenes/1"), eq(HttpMethod.DELETE),
                any(HttpEntity.class), eq(Void.class)))
                .thenReturn(ResponseEntity.noContent().build());

        assertDoesNotThrow(() -> service.eliminar(TOKEN, 1L));
    }
}
