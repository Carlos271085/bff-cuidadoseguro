package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.IndicacionMedicaDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.IndicacionMedicaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndicacionMedicaControllerTest {

    @Mock
    private IndicacionMedicaService service;

    @InjectMocks
    private IndicacionMedicaController controller;

    private static final String TOKEN = "Bearer test-token";

    private IndicacionMedicaDto dto() {
        IndicacionMedicaDto dto = new IndicacionMedicaDto();
        dto.setId(1L);
        dto.setIndicacion("Reposo 3 días");
        dto.setProfesional("Dr. Test");
        return dto;
    }

    @Test
    void listarTodos_debeRetornarLista() {
        when(service.listarTodos(TOKEN)).thenReturn(List.of(dto()));

        List<IndicacionMedicaDto> result = controller.listarTodos(TOKEN);

        assertEquals(1, result.size());
    }

    @Test
    void guardar_debeRetornarIndicacion() {
        IndicacionMedicaDto dto = dto();
        when(service.guardar(TOKEN, dto)).thenReturn(dto);

        IndicacionMedicaDto result = controller.guardar(TOKEN, dto);

        assertEquals("Reposo 3 días", result.getIndicacion());
    }

    @Test
    void buscarPorId_debeRetornarIndicacion() {
        when(service.buscarPorId(TOKEN, 1L)).thenReturn(dto());

        IndicacionMedicaDto result = controller.buscarPorId(TOKEN, 1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void eliminar_debeEjecutarseSinError() {
        doNothing().when(service).eliminar(TOKEN, 1L);

        assertDoesNotThrow(() -> controller.eliminar(TOKEN, 1L));
        verify(service).eliminar(TOKEN, 1L);
    }

    @Test
    void actualizar_debeRetornarIndicacionActualizada() {
        IndicacionMedicaDto dto = dto();
        dto.setIndicacion("Dieta blanda");
        when(service.actualizar(TOKEN, 1L, dto)).thenReturn(dto);

        IndicacionMedicaDto result = controller.actualizar(TOKEN, 1L, dto);

        assertEquals("Dieta blanda", result.getIndicacion());
    }
}
