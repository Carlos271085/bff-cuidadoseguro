package com.cuidadoseguro.bff_cuidadoseguro.service;

// Permite inyectar valores desde application.properties
import org.springframework.beans.factory.annotation.Value;

// Marca esta clase como un servicio de Spring Boot
import org.springframework.stereotype.Service;

// Permite realizar peticiones HTTP a otras APIs
import org.springframework.web.client.RestTemplate;

@Service
public class PacienteService {

    // Objeto que permitirá consumir el API Gateway
    private final RestTemplate restTemplate;

    // Obtiene automáticamente la URL configurada
    // en application.properties
    @Value("${gateway.url}")
    private String gatewayUrl;

    // Constructor para inyectar RestTemplate
    public PacienteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método que obtiene la lista de pacientes
    // desde el API Gateway
    public String obtenerPacientes() {

        // Construye la URL completa hacia el endpoint
        // del API Gateway
        String url = gatewayUrl + "/pacientes";

        // Realiza una petición GET al Gateway
        // y devuelve la respuesta como texto JSON
        return restTemplate.getForObject(url, String.class);
    }
}