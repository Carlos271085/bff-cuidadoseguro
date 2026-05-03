package com.cuidadoseguro.bff_cuidadoseguro.controller;

// Importa DTO login
import com.cuidadoseguro.bff_cuidadoseguro.dto.LoginRequest;

// Importa servicio auth
import com.cuidadoseguro.bff_cuidadoseguro.service.AuthService;

// Permite crear controladores REST
import org.springframework.web.bind.annotation.*;

@RestController

// Ruta base del controlador
@RequestMapping("/bff/auth")

public class AuthController {

    // Inyección del servicio auth
    private final AuthService authService;

    // Constructor
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint POST para login
    @PostMapping("/login")

    public String login(@RequestBody LoginRequest request) {

        // Envía datos al ms-auth
        return authService.login(request);
    }
}