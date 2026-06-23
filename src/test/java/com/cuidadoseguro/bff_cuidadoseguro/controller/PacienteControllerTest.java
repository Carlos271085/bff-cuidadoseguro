package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.PacienteDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    private static final String TOKEN = "Bearer test-token";

    private PacienteDto pacienteDto() {
        PacienteDto dto = new PacienteDto();
        dto.setId(1L);
        dto.setNombre("Juan");
        dto.setRut("12345678-9");
        return dto;
    }

    @Test
    void listar_debeRetornarOk() {
        doReturn(ResponseEntity.ok("[]")).when(pacienteService).listar(TOKEN);

        ResponseEntity<?> result = pacienteController.listar(TOKEN);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void obtener_debeRetornarPaciente() {
        when(pacienteService.obtener(TOKEN, 1L)).thenReturn(pacienteDto());

        ResponseEntity<PacienteDto> result = pacienteController.obtener(TOKEN, 1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Juan", result.getBody().getNombre());
    }

    @Test
    void crear_debeRetornarPacienteCreado() {
        PacienteDto dto = pacienteDto();
        when(pacienteService.crear(TOKEN, dto)).thenReturn(dto);

        ResponseEntity<PacienteDto> result = pacienteController.crear(TOKEN, dto);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("12345678-9", result.getBody().getRut());
    }

    @Test
    void buscarPorRut_debeRetornarPaciente() {
        doReturn(ResponseEntity.ok("{\"rut\":\"12345678-9\"}"))
                .when(pacienteService).buscarPorRut(TOKEN, "12345678-9");

        ResponseEntity<?> result = pacienteController.buscarPorRut(TOKEN, "12345678-9");

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void obtenerPorRut_debeRetornarPaciente() {
        doReturn(ResponseEntity.ok("{}"))
                .when(pacienteService).buscarPorRut(TOKEN, "12345678-9");

        ResponseEntity<?> result = pacienteController.obtenerPorRut(TOKEN, "12345678-9");

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void actualizar_debeRetornarPacienteActualizado() {
        PacienteDto dto = pacienteDto();
        dto.setNombre("Carlos");
        when(pacienteService.actualizar(TOKEN, 1L, dto)).thenReturn(dto);

        ResponseEntity<PacienteDto> result = pacienteController.actualizar(TOKEN, 1L, dto);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Carlos", result.getBody().getNombre());
    }

    @Test
    void eliminar_debeRetornarMensaje() {
        doNothing().when(pacienteService).eliminar(TOKEN, 1L);

        ResponseEntity<String> result = pacienteController.eliminar(TOKEN, 1L);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Paciente eliminado", result.getBody());
    }
}
