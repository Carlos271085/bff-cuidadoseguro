package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.FichaClinicaDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.FichaClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bff/fichas")
@RequiredArgsConstructor

public class FichaClinicaController {

    private final FichaClinicaService service;

    @GetMapping
    public List<FichaClinicaDto> listar(@RequestHeader("Authorization") String token) {
        return service.listar(token);
    }

    @PostMapping
    public FichaClinicaDto guardar(@RequestHeader("Authorization") String token, @RequestBody FichaClinicaDto ficha) {
        return service.guardar(token, ficha);
    }
    
    @PutMapping("/rut/{rutPaciente}")
    public FichaClinicaDto actualizar(@RequestHeader("Authorization") String token, 
    @PathVariable("rutPaciente") String rut, @RequestBody FichaClinicaDto ficha) {
        return service.actualizar(token, rut, ficha);
    }
}