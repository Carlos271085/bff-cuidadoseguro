package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthService authService;

    
    @Value("${gateway.url}")
    private String GATEWAY_URL;
    private static final String TOKEN = "Bearer test-token";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "gatewayUrl", GATEWAY_URL);
    }

    @Test
    void login_debeRetornarRespuesta() {
        LoginRequest request = new LoginRequest();
        request.setUsername("usuario");
        request.setPassword("clave");

        when(restTemplate.postForObject(
                eq(GATEWAY_URL + "/auth/login"),
                eq(request),
                eq(String.class)))
                .thenReturn("{\"token\":\"abc123\"}");

        String result = authService.login(request);

        assertNotNull(result);
        assertTrue(result.contains("token"));
    }

    @Test
    void getUserInfo_debeRetornarInfoUsuario() {
        String responseBody = "{\"username\":\"usuario\"}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/auth/userinfo"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
                .thenReturn(responseEntity);

        String result = authService.getUserInfo(TOKEN);

        assertEquals(responseBody, result);
    }

    @Test
    void register_debeRetornarRespuesta() {
        RegisterRequest request = RegisterRequest.builder()
                .username("nuevo")
                .password("pass")
                .email("nuevo@test.com")
                .tipoUsuario("PACIENTE")
                .nombres("Juan")
                .apellidos("Perez")
                .tipoDocumento("RUT")
                .numeroDocumento("12345678-9")
                .build();

        when(restTemplate.postForObject(
                eq(GATEWAY_URL + "/auth/register"),
                eq(request),
                eq(String.class)))
                .thenReturn("{\"success\":true}");

        String result = authService.register(request);

        assertNotNull(result);
        assertTrue(result.contains("success"));
    }

    @Test
    void refresh_debeRetornarNuevoToken() {
        RefreshRequest request = new RefreshRequest();
        request.setRefreshToken("refresh-token");

        when(restTemplate.postForObject(
                eq(GATEWAY_URL + "/auth/refresh"),
                eq(request),
                eq(String.class)))
                .thenReturn("{\"token\":\"nuevo-token\"}");

        String result = authService.refresh(request);

        assertNotNull(result);
    }

    @Test
    void logout_debeEjecutarseSinError() {
        LogoutRequest request = new LogoutRequest();
        request.setAccessToken("access-token");
        request.setRefreshToken("refresh-token");

        when(restTemplate.postForObject(
                eq(GATEWAY_URL + "/auth/logout"),
                eq(request),
                eq(String.class)))
                .thenReturn("{\"success\":true}");

        String result = authService.logout(request);

        assertNotNull(result);
        verify(restTemplate, times(1)).postForObject(anyString(), any(), eq(String.class));
    }

    @Test
    void validate_tokenValido_debeRetornarExito() {
        String token = "test-token";
        ResponseEntity<String> responseEntity = ResponseEntity.ok("{\"success\":true}");

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/auth/validate"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
                .thenReturn(responseEntity);

        String result = authService.validate(token);

        assertNotNull(result);
        assertTrue(result.contains("success"));
    }

    @Test
    void validate_tokenInvalido_debeRetornarError() {
        String token = "token-invalido";
        String errorBody = "{\"message\":\"Token expirado\"}";

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/auth/validate"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Unauthorized",
                        errorBody.getBytes(), null));

        String result = authService.validate(token);

        assertNotNull(result);
        assertTrue(result.contains("\"success\":false"));
    }

    @Test
    void health_debeRetornarEstado() {
        when(restTemplate.getForObject(
                eq(GATEWAY_URL + "/auth/health"),
                eq(String.class)))
                .thenReturn("OK");

        String result = authService.health();

        assertEquals("OK", result);
    }

    @Test
    void obtenerPacientePorRut_debeRetornarPaciente() {
        String rut = "12345678-9";
        Object paciente = new Object();

        when(restTemplate.exchange(
                eq(GATEWAY_URL + "/pacientes/rut/" + rut),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Object.class)))
                .thenReturn(ResponseEntity.ok(paciente));

        Object result = authService.obtenerPacientePorRut(rut, "token");

        assertNotNull(result);
    }
}
