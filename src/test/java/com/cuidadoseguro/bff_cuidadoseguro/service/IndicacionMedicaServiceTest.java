package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.IndicacionMedicaDto;
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
class IndicacionMedicaServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private IndicacionMedicaService service;

    private static final String GATEWAY_URL = "http://localhost:8080";
    private static final String TOKEN = "test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "gatewayUrl", GATEWAY_URL);
    }

    private IndicacionMedicaDto dto() {
        IndicacionMedicaDto dto = new IndicacionMedicaDto();
        dto.setId(1L);
        dto.setIndicacion("Reposo 3 días");
        dto.setProfesional("Dr. Test");
        return dto;
    }

    @Test
    void listarTodos_debeRetornarLista() {
        IndicacionMedicaDto[] array = { dto() };
        when(restTemplate.exchange(eq(GATEWAY_URL + "/indicaciones"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(IndicacionMedicaDto[].class)))
                .thenReturn(ResponseEntity.ok(array));

        List<IndicacionMedicaDto> result = service.listarTodos(TOKEN);

        assertEquals(1, result.size());
    }

    @Test
    void guardar_debeRetornarDto() {
        IndicacionMedicaDto dto = dto();
        when(restTemplate.exchange(eq(GATEWAY_URL + "/indicaciones"), eq(HttpMethod.POST),
                any(HttpEntity.class), eq(IndicacionMedicaDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        IndicacionMedicaDto result = service.guardar(TOKEN, dto);

        assertEquals("Reposo 3 días", result.getIndicacion());
    }

    @Test
    void buscarPorId_debeRetornarDto() {
        when(restTemplate.exchange(eq(GATEWAY_URL + "/indicaciones/1"), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(IndicacionMedicaDto.class)))
                .thenReturn(ResponseEntity.ok(dto()));

        IndicacionMedicaDto result = service.buscarPorId(TOKEN, 1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void eliminar_debeEjecutarseSinError() {
        when(restTemplate.exchange(eq(GATEWAY_URL + "/indicaciones/1"), eq(HttpMethod.DELETE),
                any(HttpEntity.class), eq(Void.class)))
                .thenReturn(ResponseEntity.noContent().build());

        assertDoesNotThrow(() -> service.eliminar(TOKEN, 1L));
    }

    @Test
    void actualizar_debeRetornarDtoActualizado() {
        IndicacionMedicaDto dto = dto();
        dto.setIndicacion("Dieta blanda");
        when(restTemplate.exchange(eq(GATEWAY_URL + "/indicaciones/1"), eq(HttpMethod.PUT),
                any(HttpEntity.class), eq(IndicacionMedicaDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        IndicacionMedicaDto result = service.actualizar(TOKEN, 1L, dto);

        assertEquals("Dieta blanda", result.getIndicacion());
    }
}
