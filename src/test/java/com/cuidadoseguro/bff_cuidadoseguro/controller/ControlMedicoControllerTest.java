package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.ControlMedicoDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.ControlMedicoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControlMedicoControllerTest {

    @Mock
    private ControlMedicoService service;

    @InjectMocks
    private ControlMedicoController controller;

    private static final String TOKEN = "Bearer test-token";

    private ControlMedicoDto dto() {
        ControlMedicoDto dto = new ControlMedicoDto();
        dto.setId(1L);
        dto.setFecha("2024-03-15");
        dto.setDiagnostico("Hipertensión");
        return dto;
    }

    @Test
    void listarTodos_debeRetornarLista() {
        when(service.listarTodos(TOKEN)).thenReturn(List.of(dto()));

        ResponseEntity<List<ControlMedicoDto>> result = controller.listarTodos(TOKEN);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void guardar_debeRetornarControl() {
        ControlMedicoDto dto = dto();
        when(service.guardar(TOKEN, dto)).thenReturn(dto);

        ResponseEntity<ControlMedicoDto> result = controller.guardar(TOKEN, dto);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Hipertensión", result.getBody().getDiagnostico());
    }

    @Test
    void buscarPorId_debeRetornarControl() {
        when(service.buscarPorId(TOKEN, 1L)).thenReturn(dto());

        ResponseEntity<ControlMedicoDto> result = controller.buscarPorId(TOKEN, 1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void eliminar_debeRetornarNoContent() {
        doNothing().when(service).eliminar(TOKEN, 1L);

        ResponseEntity<Void> result = controller.eliminar(TOKEN, 1L);

        assertEquals(204, result.getStatusCode().value());
    }
}
