package com.cuidadoseguro.bff_cuidadoseguro.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entidad que representa medicamentos asociados a una ficha clinica
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoDto {

    private Long id;
    private String nombre;
    private String dosis;
    private String frecuencia;
    private FichaClinicaDto ficha;
}