package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.FichaClinicaDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.FichaClinicaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FichaClinicaControllerTest {

    @Mock
    private FichaClinicaService service;

    @InjectMocks
    private FichaClinicaController controller;

    private static final String TOKEN = "Bearer test-token";

    private FichaClinicaDto dto() {
        FichaClinicaDto dto = new FichaClinicaDto();
        dto.setId(1L);
        dto.setNombrePaciente("Juan Perez");
        dto.setRutPaciente("12345678-9");
        return dto;
    }

    @Test
    void listar_debeRetornarLista() {
        when(service.listar(TOKEN)).thenReturn(List.of(dto()));

        List<FichaClinicaDto> result = controller.listar(TOKEN);

        assertEquals(1, result.size());
        assertEquals("Juan Perez", result.get(0).getNombrePaciente());
    }

    @Test
    void guardar_debeRetornarFichaCreada() {
        FichaClinicaDto ficha = dto();
        when(service.guardar(TOKEN, ficha)).thenReturn(ficha);

        FichaClinicaDto result = controller.guardar(TOKEN, ficha);

        assertNotNull(result);
        assertEquals("12345678-9", result.getRutPaciente());
    }
}
