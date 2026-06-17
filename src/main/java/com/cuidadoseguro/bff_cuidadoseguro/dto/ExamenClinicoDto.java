package com.cuidadoseguro.bff_cuidadoseguro.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamenClinicoDto{

    private Long id;

    private String nombre;

    private LocalDateTime fechaRegistro;

    private String estado;

    private String profesional;

    private String observacion;

    private String resultado;

    private Long ficha;
}