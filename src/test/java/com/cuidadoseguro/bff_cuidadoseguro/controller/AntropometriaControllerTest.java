package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.AntropometriaDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.AntropometriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AntropometriaControllerTest {

    @Mock
    private AntropometriaService service;

    @InjectMocks
    private AntropometriaController controller;

    private static final String AUTH_HEADER = "Bearer test-token";
    private static final String TOKEN = "test-token"; // sin "Bearer "

    private AntropometriaDto dto() {
        AntropometriaDto dto = new AntropometriaDto();
        dto.setId(1L);
        dto.setPeso(70.0);
        dto.setAltura(1.75);
        return dto;
    }

    @Test
    void guardar_debeQuitarBearerYLlamarServicio() {
        AntropometriaDto dto = dto();
        when(service.guardar(1L, dto, TOKEN)).thenReturn(dto);

        AntropometriaDto result = controller.guardar(1L, dto, AUTH_HEADER);

        assertNotNull(result);
        assertEquals(70.0, result.getPeso());
        verify(service).guardar(1L, dto, TOKEN);
    }

    @Test
    void listar_debeQuitarBearerYRetornarLista() {
        when(service.listar(1L, TOKEN)).thenReturn(List.of(dto(), dto()));

        List<AntropometriaDto> result = controller.listar(1L, AUTH_HEADER);

        assertEquals(2, result.size());
        verify(service).listar(1L, TOKEN);
    }
}
