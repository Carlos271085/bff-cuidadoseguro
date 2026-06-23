package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.EvolucionClinicaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvolucionClinicaServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EvolucionClinicaService service;

    private static final String GATEWAY_URL = "http://localhost:8080";
    private static final String TOKEN = "test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "gatewayUrl", GATEWAY_URL);
    }

    private EvolucionClinicaDto dto() {
        EvolucionClinicaDto dto = new EvolucionClinicaDto();
        dto.setId(1L);
        dto.setDescripcion("Evolución favorable");
        return dto;
    }

    @Test
    void listarTodos_debeRetornarLista() {
        EvolucionClinicaDto[] array = { dto(), dto() };
        when(restTemplate.exchange(eq(GATEWAY_URL + "/evoluciones"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(EvolucionClinicaDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        List<EvolucionClinicaDto> result = service.listarTodos(TOKEN);

        assertEquals(2, result.size());
    }

    @Test
    void guardar_debeRetornarDto() {
        EvolucionClinicaDto dto = dto();
        when(restTemplate.exchange(eq(GATEWAY_URL + "/evoluciones"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(EvolucionClinicaDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        EvolucionClinicaDto result = service.guardar(TOKEN, dto);

        assertNotNull(result);
        assertEquals("Evolución favorable", result.getDescripcion());
    }

    @Test
    void buscarPorId_debeRetornarDto() {
        when(restTemplate.exchange(eq(GATEWAY_URL + "/evoluciones/1"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(EvolucionClinicaDto.class)))
                .thenReturn(ResponseEntity.ok(dto()));

        EvolucionClinicaDto result = service.buscarPorId(TOKEN, 1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void eliminar_debeEjecutarseSinError() {
        when(restTemplate.exchange(eq(GATEWAY_URL + "/evoluciones/1"), eq(HttpMethod.DELETE),
                any(HttpEntity.class), eq(Void.class)))
                .thenReturn(ResponseEntity.noContent().build());

        assertDoesNotThrow(() -> service.eliminar(TOKEN, 1L));
    }

    @Test
    void actualizar_debeRetornarDtoActualizado() {
        EvolucionClinicaDto dto = dto();
        dto.setDescripcion("Actualizada");
        when(restTemplate.exchange(eq(GATEWAY_URL + "/evoluciones/1"), eq(HttpMethod.PUT),
                any(HttpEntity.class), eq(EvolucionClinicaDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        EvolucionClinicaDto result = service.actualizar(TOKEN, 1L, dto);

        assertEquals("Actualizada", result.getDescripcion());
    }
}
