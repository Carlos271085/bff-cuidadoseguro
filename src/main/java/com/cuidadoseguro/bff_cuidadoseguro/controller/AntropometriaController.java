package com.cuidadoseguro.bff_cuidadoseguro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.cuidadoseguro.bff_cuidadoseguro.dto.AntropometriaDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.AntropometriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bff/antropometrias")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AntropometriaController {

    private final AntropometriaService service;

    @PostMapping(value = "/{fichaId}",
    consumes = "application/json")
    public AntropometriaDto guardar(
            @PathVariable Long fichaId,
            @RequestBody AntropometriaDto dto,
            @RequestHeader("Authorization")
            String authorization) {

        String token =
                authorization.replace("Bearer ", "");

        return service.guardar(
                fichaId,
                dto,
                token);
    }

    @GetMapping("/{fichaId}")
    public List<AntropometriaDto> listar(
            @PathVariable Long fichaId,
            @RequestHeader("Authorization")
            String authorization) {

        String token =
                authorization.replace("Bearer ", "");

        return service.listar(
                fichaId,
                token);
    }
}