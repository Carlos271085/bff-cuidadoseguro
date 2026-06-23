package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.EvolucionClinicaDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.EvolucionClinicaService;
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
class EvolucionClinicaControllerTest {

    @Mock
    private EvolucionClinicaService service;

    @InjectMocks
    private EvolucionClinicaController controller;

    private static final String TOKEN = "Bearer test-token";

    private EvolucionClinicaDto dto() {
        EvolucionClinicaDto dto = new EvolucionClinicaDto();
        dto.setId(1L);
        dto.setDescripcion("Evolución favorable");
        dto.setProfesional("Dr. Test");
        return dto;
    }

    @Test
    void listarTodos_debeRetornarLista() {
        when(service.listarTodos(TOKEN)).thenReturn(List.of(dto()));

        ResponseEntity<List<EvolucionClinicaDto>> result = controller.listarTodos(TOKEN);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void guardar_debeRetornarEvolucionCreada() {
        EvolucionClinicaDto dto = dto();
        when(service.guardar(TOKEN, dto)).thenReturn(dto);

        ResponseEntity<EvolucionClinicaDto> result = controller.guardar(TOKEN, dto);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Evolución favorable", result.getBody().getDescripcion());
    }

    @Test
    void buscarPorId_debeRetornarEvolucion() {
        when(service.buscarPorId(TOKEN, 1L)).thenReturn(dto());

        ResponseEntity<EvolucionClinicaDto> result = controller.buscarPorId(TOKEN, 1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void eliminar_debeRetornarNoContent() {
        doNothing().when(service).eliminar(TOKEN, 1L);

        ResponseEntity<Void> result = controller.eliminar(TOKEN, 1L);

        assertEquals(204, result.getStatusCode().value());
        verify(service, times(1)).eliminar(TOKEN, 1L);
    }

    @Test
    void actualizar_debeRetornarEvolucionActualizada() {
        EvolucionClinicaDto dto = dto();
        dto.setDescripcion("Actualizada");
        when(service.actualizar(TOKEN, 1L, dto)).thenReturn(dto);

        ResponseEntity<EvolucionClinicaDto> result = controller.actualizar(TOKEN, 1L, dto);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Actualizada", result.getBody().getDescripcion());
    }
}
