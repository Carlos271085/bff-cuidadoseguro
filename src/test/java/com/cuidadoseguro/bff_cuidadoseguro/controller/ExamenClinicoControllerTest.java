package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.ExamenClinicoDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.ExamenClinicoService;
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
class ExamenClinicoControllerTest {

    @Mock
    private ExamenClinicoService service;

    @InjectMocks
    private ExamenClinicoController controller;

    private static final String TOKEN = "Bearer test-token";

    private ExamenClinicoDto dto() {
        ExamenClinicoDto dto = new ExamenClinicoDto();
        dto.setId(1L);
        dto.setNombre("Hemograma");
        dto.setEstado("Pendiente");
        return dto;
    }

    @Test
    void listarTodos_debeRetornarOk() {
        when(service.listarTodos(TOKEN)).thenReturn(List.of(dto()));

        ResponseEntity<?> result = controller.listarTodos(TOKEN);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void guardar_debeRetornarExamen() {
        ExamenClinicoDto dto = dto();
        when(service.guardar(TOKEN, dto)).thenReturn(dto);

        ExamenClinicoDto result = controller.guardar(TOKEN, dto);

        assertNotNull(result);
        assertEquals("Hemograma", result.getNombre());
    }

    @Test
    void actualizar_debeRetornarExamenActualizado() {
        ExamenClinicoDto dto = dto();
        dto.setEstado("Completado");
        when(service.actualizar(TOKEN, 1L, dto)).thenReturn(dto);

        ExamenClinicoDto result = controller.actualizar(TOKEN, 1L, dto);

        assertEquals("Completado", result.getEstado());
    }

    @Test
    void buscarPorId_debeRetornarExamen() {
        when(service.buscarPorId(TOKEN, 1L)).thenReturn(dto());

        ExamenClinicoDto result = controller.buscarPorId(TOKEN, 1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void eliminar_debeEjecutarseSinError() {
        doNothing().when(service).eliminar(TOKEN, 1L);

        assertDoesNotThrow(() -> controller.eliminar(TOKEN, 1L));
        verify(service).eliminar(TOKEN, 1L);
    }
}
