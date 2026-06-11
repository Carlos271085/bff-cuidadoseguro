package com.cuidadoseguro.bff_cuidadoseguro.dto;

import lombok.Data;

@Data
public class AntropometriaDto {

    private Long id;

    private Double peso;

    private Double altura;

    private String fechaRegistro;

    private Long fichaId;
}