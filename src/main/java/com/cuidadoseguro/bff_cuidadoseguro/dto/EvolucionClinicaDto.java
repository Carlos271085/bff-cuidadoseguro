package com.cuidadoseguro.bff_cuidadoseguro.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EvolucionClinicaDto {

    private Long id;
    private LocalDateTime fechaRegistro;
    private String descripcion;
    private String observaciones;
    private Long pacienteId;
}