package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.dto.SignosVitalesDto;
import com.cuidadoseguro.bff_cuidadoseguro.service.SignosVitalesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignosVitalesControllerTest {

    @Mock
    private SignosVitalesService service;

    @InjectMocks
    private SignosVitalesController controller;

    private static final String TOKEN = "Bearer test-token";

    private SignosVitalesDto dto() {
        return new SignosVitalesDto(1L, "120/80", 72, 36.5, 98, "Enf. López", null, null);
    }

    @Test
    void listarTodos_debeRetornarLista() {
        when(service.listarTodos(TOKEN)).thenReturn(List.of(dto()));

        List<SignosVitalesDto> result = controller.listarTodos(TOKEN);

        assertEquals(1, result.size());
    }

    @Test
    void guardar_debeRetornarDto() {
        SignosVitalesDto dto = dto();
        when(service.guardar(TOKEN, 1L, dto)).thenReturn(dto);

        SignosVitalesDto result = controller.guardar(TOKEN, 1L, dto);

        assertNotNull(result);
        assertEquals("120/80", result.getPresion());
    }

    @Test
    void buscarPorId_debeRetornarDto() {
        when(service.buscarPorId(TOKEN, 1L)).thenReturn(dto());

        SignosVitalesDto result = controller.buscarPorId(TOKEN, 1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void eliminar_debeEjecutarseSinError() {
        doNothing().when(service).eliminar(TOKEN, 1L);

        assertDoesNotThrow(() -> controller.eliminar(TOKEN, 1L));
        verify(service, times(1)).eliminar(TOKEN, 1L);
    }

    @Test
    void listarPorFicha_debeRetornarLista() {
        when(service.listarPorFicha(TOKEN, 1L)).thenReturn(List.of(dto(), dto()));

        List<SignosVitalesDto> result = controller.listarPorFicha(TOKEN, 1L);

        assertEquals(2, result.size());
    }
}
