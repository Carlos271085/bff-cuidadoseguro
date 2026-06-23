package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.PacienteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PacienteService pacienteService;

    private static final String GATEWAY_URL = "http://localhost:8080";
    private static final String TOKEN = "Bearer test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(pacienteService, "gatewayUrl", GATEWAY_URL);
    }

    private PacienteDto crearPacienteDto() {
        PacienteDto dto = new PacienteDto();
        dto.setId(1L);
        dto.setRut("12345678-9");
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        dto.setGenero("M");
        return dto;
    }

    @Test
    void buscarPorRut_exitoso_debeRetornarOk() {
        String rut = "12345678-9";
        ResponseEntity<String> response = ResponseEntity.ok("{\"rut\":\"12345678-9\"}");

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes/rut/" + rut),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
                .thenReturn(response);

        ResponseEntity<?> result = pacienteService.buscarPorRut(TOKEN, rut);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void buscarPorRut_notFound_debeRetornarError() {
        String rut = "99999999-9";
        String errorBody = "{\"message\":\"Paciente no encontrado\"}";

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes/rut/" + rut),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found",
                        errorBody.getBytes(), null));

        ResponseEntity<?> result = pacienteService.buscarPorRut(TOKEN, rut);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void listar_exitoso_debeRetornarOk() {
        ResponseEntity<String> response = ResponseEntity.ok("[{\"id\":1}]");

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
                .thenReturn(response);

        ResponseEntity<?> result = pacienteService.listar(TOKEN);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void listar_error_debeRetornarInternalServerError() {
        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "Error",
                        "invalid json".getBytes(), null));

        ResponseEntity<?> result = pacienteService.listar(TOKEN);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void obtener_exitoso_debeRetornarPaciente() {
        PacienteDto paciente = crearPacienteDto();

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes/1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(PacienteDto.class)))
                .thenReturn(ResponseEntity.ok(paciente));

        PacienteDto result = pacienteService.obtener(TOKEN, 1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void obtener_error_debeLanzarExcepcion() {
        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes/99"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(PacienteDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found"));

        assertThrows(RuntimeException.class, () -> pacienteService.obtener(TOKEN, 99L));
    }

    @Test
    void crear_exitoso_debeRetornarPacienteCreado() {
        PacienteDto paciente = crearPacienteDto();

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(PacienteDto.class)))
                .thenReturn(ResponseEntity.ok(paciente));

        PacienteDto result = pacienteService.crear(TOKEN, paciente);

        assertNotNull(result);
        assertEquals("12345678-9", result.getRut());
    }

    @Test
    void crear_error_debeLanzarExcepcion() {
        PacienteDto paciente = crearPacienteDto();

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(PacienteDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request"));

        assertThrows(RuntimeException.class, () -> pacienteService.crear(TOKEN, paciente));
    }

    @Test
    void actualizar_exitoso_debeRetornarPacienteActualizado() {
        PacienteDto paciente = crearPacienteDto();
        paciente.setNombre("Carlos");

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes/1"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(PacienteDto.class)))
                .thenReturn(ResponseEntity.ok(paciente));

        PacienteDto result = pacienteService.actualizar(TOKEN, 1L, paciente);

        assertNotNull(result);
        assertEquals("Carlos", result.getNombre());
    }

    @Test
    void eliminar_exitoso_noDebeLanzarExcepcion() {
        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes/1"),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Void.class)))
                .thenReturn(ResponseEntity.noContent().build());

        assertDoesNotThrow(() -> pacienteService.eliminar(TOKEN, 1L));
    }

    @Test
    void eliminar_error_debeLanzarExcepcion() {
        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes/99"),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Void.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found"));

        assertThrows(RuntimeException.class, () -> pacienteService.eliminar(TOKEN, 99L));
    }
}
