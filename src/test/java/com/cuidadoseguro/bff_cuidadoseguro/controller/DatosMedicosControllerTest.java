package com.cuidadoseguro.bff_cuidadoseguro.controller;

import com.cuidadoseguro.bff_cuidadoseguro.service.DatosMedicosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatosMedicosControllerTest {

    @Mock
    private DatosMedicosService datosMedicosService;

    @InjectMocks
    private DatosMedicosController controller;

    @Test
    void obtenerSignosVitales_debeRetornarDatos() {
        when(datosMedicosService.obtenerSignosVitales("token")).thenReturn("[{\"presion\":\"120/80\"}]");

        String result = controller.obtenerSignosVitales("token");

        assertNotNull(result);
        assertTrue(result.contains("presion"));
    }
}
