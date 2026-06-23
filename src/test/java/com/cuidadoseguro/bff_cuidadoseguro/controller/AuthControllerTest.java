package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.*;
import com.cuidadoseguro.bff_cuidadoseguro.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_debeRetornarOk() {
        LoginRequest request = new LoginRequest();
        request.setUsername("usuario");
        request.setPassword("clave");

        when(authService.login(request)).thenReturn("{\"token\":\"abc\"}");

        ResponseEntity<String> result = authController.login(request);

        assertEquals(200, result.getStatusCode().value());
        assertTrue(result.getBody().contains("token"));
    }

    @Test
    void userInfo_debeRetornarInfo() {
        when(authService.getUserInfo("Bearer token")).thenReturn("{\"username\":\"user\"}");

        ResponseEntity<String> result = authController.userInfo("Bearer token");

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void register_debeRetornarOk() {
        RegisterRequest request = RegisterRequest.builder()
                .username("nuevo").password("pass").email("a@b.cl")
                .tipoUsuario("PACIENTE").nombres("Juan").apellidos("Perez")
                .tipoDocumento("RUT").numeroDocumento("11111111-1").build();

        when(authService.register(request)).thenReturn("{\"success\":true}");

        ResponseEntity<String> result = authController.register(request);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void refresh_debeRetornarNuevoToken() {
        RefreshRequest request = new RefreshRequest("refresh-token");
        when(authService.refresh(request)).thenReturn("{\"token\":\"nuevo\"}");

        ResponseEntity<String> result = authController.refresh(request);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void logout_debeRetornarOk() {
        LogoutRequest request = new LogoutRequest("access", "refresh");
        when(authService.logout(request)).thenReturn("{\"success\":true}");

        ResponseEntity<String> result = authController.logout(request);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void validate_debeRetornarResultado() {
        when(authService.validate("mi-token")).thenReturn("{\"success\":true}");

        ResponseEntity<String> result = authController.validate("mi-token");

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void health_debeRetornarOk() {
        when(authService.health()).thenReturn("OK");

        ResponseEntity<String> result = authController.health();

        assertEquals(200, result.getStatusCode().value());
        assertEquals("OK", result.getBody());
    }

    @Test
    void obtenerPacientePorRut_debeRetornarPaciente() {
        when(authService.obtenerPacientePorRut("12345678-9", "token")).thenReturn("{}");

        ResponseEntity<?> result = authController.obtenerPacientePorRut("12345678-9", "Bearer token");

        assertEquals(200, result.getStatusCode().value());
    }
}
