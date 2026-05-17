package com.cuidadoseguro.bff_cuidadoseguro.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private Long id;
    private String username;
    private String email;
    private String nombreCompleto;
    private String tipoUsuario;
    private List<String> roles;
}