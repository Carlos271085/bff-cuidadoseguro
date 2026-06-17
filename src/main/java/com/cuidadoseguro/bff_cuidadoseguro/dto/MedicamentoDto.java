package com.cuidadoseguro.bff_cuidadoseguro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoDto {

    private Long id;

    private String nombre;
    private String dosis;
    private String frecuencia;
    private String observaciones;
    private Integer diasTratamiento;
    private LocalDateTime fechaRegistro;

    private String profesional;

    private Long ficha;
}