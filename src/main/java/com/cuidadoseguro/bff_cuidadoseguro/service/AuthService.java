package com.cuidadoseguro.bff_cuidadoseguro.service;

// Importa el DTO del login
import com.cuidadoseguro.bff_cuidadoseguro.dto.LoginRequest;

// Permite leer valores desde application.properties
import org.springframework.beans.factory.annotation.Value;

// Permite crear servicios Spring
import org.springframework.stereotype.Service;

// Cliente HTTP para consumir otros microservicios
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    // Inyección de RestTemplate
    private final RestTemplate restTemplate;

    // URL del microservicio auth
    @Value("${auth.url}")
    private String authUrl;

    // Constructor
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para enviar login al ms-auth
    public String login(LoginRequest request) {

        // Endpoint login del ms-auth
        String url = authUrl + "/auth/login";

        // Envía el request y obtiene respuesta
        return restTemplate.postForObject(
                url,
                request,
                String.class
        );
    }
}