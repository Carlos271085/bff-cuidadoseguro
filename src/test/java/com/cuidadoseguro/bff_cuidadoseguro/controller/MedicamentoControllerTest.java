package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.MedicamentoDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.MedicamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicamentoControllerTest {

    @Mock
    private MedicamentoService service;

    @InjectMocks
    private MedicamentoController controller;

    private static final String TOKEN = "Bearer test-token";

    private MedicamentoDto dto() {
        MedicamentoDto dto = new MedicamentoDto();
        dto.setId(1L);
        dto.setNombre("Ibuprofeno");
        dto.setDosis("400mg");
        return dto;
    }

    @Test
    void listar_debeRetornarLista() {
        when(service.listar(TOKEN)).thenReturn(List.of(dto()));

        List<MedicamentoDto> result = controller.listar(TOKEN);

        assertEquals(1, result.size());
        assertEquals("Ibuprofeno", result.get(0).getNombre());
    }

    @Test
    void guardar_debeRetornarMedicamento() {
        MedicamentoDto dto = dto();
        when(service.guardar(TOKEN, dto)).thenReturn(dto);

        MedicamentoDto result = controller.guardar(TOKEN, dto);

        assertNotNull(result);
        assertEquals("400mg", result.getDosis());
    }

    @Test
    void actualizar_debeRetornarMedicamentoActualizado() {
        MedicamentoDto dto = dto();
        dto.setDosis("800mg");
        when(service.actualizar(TOKEN, 1L, dto)).thenReturn(dto);

        MedicamentoDto result = controller.actualizar(TOKEN, 1L, dto);

        assertEquals("800mg", result.getDosis());
    }
}
