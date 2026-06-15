package com.cuidadoseguro.bff_cuidadoseguro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicacionMedicaDto {

    private Long id;

    private LocalDateTime fechaRegistro;

    private String profesional;

    private String indicacion;

    private FichaClinicaDto ficha;

}