package com.cuidadoseguro.bff_cuidadoseguro.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignosVitalesDto {

    private Long id;

    private String presion;

    private Integer frecuencia;

    private Double temperatura;

    private Integer saturacion;

    private String profesional;

    private LocalDateTime fecha;
    private FichaClinicaDto ficha;

}