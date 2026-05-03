package com.cuidadoseguro.bff_cuidadoseguro.controller;

// Permite crear endpoints REST
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Importamos el servicio que consume el API Gateway
import com.cuidadoseguro.bff_cuidadoseguro.service.PacienteService;

@RestController

// Ruta base del controlador
@RequestMapping("/bff")
public class PacienteController {

    // Servicio que se conectará al API Gateway
    private final PacienteService pacienteService;

    // Constructor para inyectar el servicio
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // Endpoint GET para obtener pacientes
    @GetMapping("/pacientes")
    public String obtenerPacientes() {

        // Llama al servicio y devuelve la respuesta
        // obtenida desde el API Gateway
        return pacienteService.obtenerPacientes();
    }
}