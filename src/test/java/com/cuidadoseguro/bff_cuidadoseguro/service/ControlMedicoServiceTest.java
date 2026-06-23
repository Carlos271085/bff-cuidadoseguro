package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.ControlMedicoDto;
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
class ControlMedicoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ControlMedicoService service;

    private static final String GATEWAY_URL = "http://localhost:8080";
    private static final String TOKEN = "test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "gatewayUrl", GATEWAY_URL);
    }

    private ControlMedicoDto dto() {
        ControlMedicoDto dto = new ControlMedicoDto();
        dto.setId(1L);
        dto.setFecha("2024-03-15");
        dto.setDiagnostico("Hipertensión");
        return dto;
    }

    @Test
    void listarTodos_debeRetornarLista() {
        ControlMedicoDto[] array = { dto(), dto() };
        when(restTemplate.exchange(eq(GATEWAY_URL + "/controles"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(ControlMedicoDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        List<ControlMedicoDto> result = service.listarTodos(TOKEN);

        assertEquals(2, result.size());
    }

    @Test
    void guardar_debeRetornarDto() {
        ControlMedicoDto dto = dto();
        when(restTemplate.exchange(eq(GATEWAY_URL + "/controles"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(ControlMedicoDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        ControlMedicoDto result = service.guardar(TOKEN, dto);

        assertNotNull(result);
        assertEquals("Hipertensión", result.getDiagnostico());
    }

    @Test
    void buscarPorId_debeRetornarDto() {
        when(restTemplate.exchange(eq(GATEWAY_URL + "/controles/1"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(ControlMedicoDto.class)))
                .thenReturn(ResponseEntity.ok(dto()));

        ControlMedicoDto result = service.buscarPorId(TOKEN, 1L);

        assertEquals(1L, result.getId());
        assertEquals("2024-03-15", result.getFecha());
    }

    @Test
    void eliminar_debeEjecutarseSinError() {
        when(restTemplate.exchange(eq(GATEWAY_URL + "/controles/1"), eq(HttpMethod.DELETE),
                any(HttpEntity.class), eq(Void.class)))
                .thenReturn(ResponseEntity.noContent().build());

        assertDoesNotThrow(() -> service.eliminar(TOKEN, 1L));
        verify(restTemplate).exchange(eq(GATEWAY_URL + "/controles/1"),
                eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class));
    }
}
