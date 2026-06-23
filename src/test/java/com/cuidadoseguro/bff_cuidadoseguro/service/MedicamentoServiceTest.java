package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.MedicamentoDto;
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
class MedicamentoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MedicamentoService service;

    private static final String GATEWAY_URL = "http://localhost:8080";
    private static final String TOKEN = "Bearer test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "gatewayUrl", GATEWAY_URL);
    }

    private MedicamentoDto dto() {
        MedicamentoDto dto = new MedicamentoDto();
        dto.setId(1L);
        dto.setNombre("Ibuprofeno");
        dto.setDosis("400mg");
        return dto;
    }

    @Test
    void listar_debeRetornarLista() {
        MedicamentoDto[] array = { dto() };
        when(restTemplate.exchange(eq(GATEWAY_URL + "/medicamentos"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(MedicamentoDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        List<MedicamentoDto> result = service.listar(TOKEN);

        assertEquals(1, result.size());
        assertEquals("Ibuprofeno", result.get(0).getNombre());
    }

    @Test
    void listar_respuestaNull_debeRetornarListaVacia() {
        when(restTemplate.exchange(eq(GATEWAY_URL + "/medicamentos"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(MedicamentoDto[].class)))
                .thenReturn(ResponseEntity.ok(null));

        List<MedicamentoDto> result = service.listar(TOKEN);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void guardar_debeRetornarDto() {
        MedicamentoDto dto = dto();
        when(restTemplate.exchange(eq(GATEWAY_URL + "/medicamentos"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(MedicamentoDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        MedicamentoDto result = service.guardar(TOKEN, dto);

        assertNotNull(result);
        assertEquals("400mg", result.getDosis());
    }

    @Test
    void actualizar_debeRetornarDtoActualizado() {
        MedicamentoDto dto = dto();
        dto.setDosis("800mg");
        when(restTemplate.exchange(eq(GATEWAY_URL + "/medicamentos/1"), eq(HttpMethod.PUT),
                any(HttpEntity.class), eq(MedicamentoDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        MedicamentoDto result = service.actualizar(TOKEN, 1L, dto);

        assertEquals("800mg", result.getDosis());
    }
}
