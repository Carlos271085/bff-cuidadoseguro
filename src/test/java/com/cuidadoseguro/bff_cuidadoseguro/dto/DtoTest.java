package com.cuidadoseguro.bff_cuidadoseguro.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests exhaustivos para todos los DTOs del proyecto.
 * Se cubren: getters, setters, constructores, equals, hashCode, toString.
 */
class DtoTest {

    // ══════════════════════════════════════════════
    // PacienteDto
    // ══════════════════════════════════════════════

    @Test
    void pacienteDto_todosLosGettersSetters() {
        LocalDate fecha = LocalDate.of(1990, 5, 15);
        PacienteDto dto = new PacienteDto();
        dto.setId(1L);
        dto.setRut("12345678-9");
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setFechaNacimiento(fecha);
        dto.setGenero("M");
        dto.setDiagnostico("Ninguno");
        dto.setAlergias("Penicilina");
        dto.setObservaciones("Sin observaciones");
        dto.setDireccion("Av. Principal 123");
        dto.setCiudad("Santiago");
        dto.setTelefono("+56912345678");
        dto.setEmail("juan@test.com");
        dto.setCentroMedico("Hospital Central");
        dto.setTutorResponsable("María Perez");
        dto.setParentescoTutor("Madre");
        dto.setImagenUrl("http://img.test/foto.png");

        assertEquals(1L, dto.getId());
        assertEquals("12345678-9", dto.getRut());
        assertEquals("Juan", dto.getNombre());
        assertEquals("Perez", dto.getApellido());
        assertEquals(fecha, dto.getFechaNacimiento());
        assertEquals("M", dto.getGenero());
        assertEquals("Ninguno", dto.getDiagnostico());
        assertEquals("Penicilina", dto.getAlergias());
        assertEquals("Sin observaciones", dto.getObservaciones());
        assertEquals("Av. Principal 123", dto.getDireccion());
        assertEquals("Santiago", dto.getCiudad());
        assertEquals("+56912345678", dto.getTelefono());
        assertEquals("juan@test.com", dto.getEmail());
        assertEquals("Hospital Central", dto.getCentroMedico());
        assertEquals("María Perez", dto.getTutorResponsable());
        assertEquals("Madre", dto.getParentescoTutor());
        assertEquals("http://img.test/foto.png", dto.getImagenUrl());
    }

    @Test
    void pacienteDto_constructorCompleto() {
        LocalDate fecha = LocalDate.of(2000, 1, 1);
        PacienteDto dto = new PacienteDto(1L, "11111111-1", "Ana", "García", fecha,
                "F", "Diabetes", "Ninguna", "Control", "Los Leones 10",
                "Valparaíso", "+56911111111", "ana@test.com", "Clínica X",
                "Carlos García", "Padre", null);

        assertEquals("Ana", dto.getNombre());
        assertEquals(fecha, dto.getFechaNacimiento());
    }

    @Test
    void pacienteDto_equalsHashCodeToString() {
        PacienteDto dto1 = new PacienteDto();
        dto1.setId(1L);
        dto1.setNombre("Juan");
        dto1.setRut("12345678-9");

        PacienteDto dto2 = new PacienteDto();
        dto2.setId(1L);
        dto2.setNombre("Juan");
        dto2.setRut("12345678-9");

        PacienteDto dto3 = new PacienteDto();
        dto3.setId(2L);
        dto3.setNombre("Pedro");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("string"));

        String str = dto1.toString();
        assertNotNull(str);
        assertTrue(str.contains("Juan"));
    }

    // ══════════════════════════════════════════════
    // AntropometriaDto
    // ══════════════════════════════════════════════

    @Test
    void antropometriaDto_todosLosGettersSetters() {
        AntropometriaDto dto = new AntropometriaDto();
        dto.setId(1L);
        dto.setPeso(75.0);
        dto.setAltura(1.72);
        dto.setFechaRegistro("2024-01-15");
        dto.setFichaId(10L);
        dto.setProfesional("Dr. Muñoz");

        assertEquals(1L, dto.getId());
        assertEquals(75.0, dto.getPeso());
        assertEquals(1.72, dto.getAltura());
        assertEquals("2024-01-15", dto.getFechaRegistro());
        assertEquals(10L, dto.getFichaId());
        assertEquals("Dr. Muñoz", dto.getProfesional());
    }

    @Test
    void antropometriaDto_equalsHashCodeToString() {
        AntropometriaDto dto1 = new AntropometriaDto();
        dto1.setId(1L);
        dto1.setPeso(70.0);
        dto1.setAltura(1.75);
        dto1.setFechaRegistro("2024-01-01");
        dto1.setFichaId(5L);
        dto1.setProfesional("Dr. A");

        AntropometriaDto dto2 = new AntropometriaDto();
        dto2.setId(1L);
        dto2.setPeso(70.0);
        dto2.setAltura(1.75);
        dto2.setFechaRegistro("2024-01-01");
        dto2.setFichaId(5L);
        dto2.setProfesional("Dr. A");

        AntropometriaDto dto3 = new AntropometriaDto();
        dto3.setId(2L);
        dto3.setPeso(80.0);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("other"));

        String str = dto1.toString();
        assertNotNull(str);
        assertTrue(str.contains("70.0"));
    }

    // ══════════════════════════════════════════════
    // LoginRequest
    // ══════════════════════════════════════════════

    @Test
    void loginRequest_todosLosGettersSetters() {
        LoginRequest dto = new LoginRequest();
        dto.setUsername("usuario123");
        dto.setPassword("clave456");

        assertEquals("usuario123", dto.getUsername());
        assertEquals("clave456", dto.getPassword());
    }

    @Test
    void loginRequest_equalsHashCodeToString() {
        LoginRequest dto1 = new LoginRequest();
        dto1.setUsername("user");
        dto1.setPassword("pass");

        LoginRequest dto2 = new LoginRequest();
        dto2.setUsername("user");
        dto2.setPassword("pass");

        LoginRequest dto3 = new LoginRequest();
        dto3.setUsername("otro");
        dto3.setPassword("otra");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(42));

        String str = dto1.toString();
        assertNotNull(str);
        assertTrue(str.contains("user"));
    }

    // ══════════════════════════════════════════════
    // LogoutRequest
    // ══════════════════════════════════════════════

    @Test
    void logoutRequest_todosLosGettersSetters() {
        LogoutRequest dto = new LogoutRequest();
        dto.setAccessToken("access-abc");
        dto.setRefreshToken("refresh-xyz");

        assertEquals("access-abc", dto.getAccessToken());
        assertEquals("refresh-xyz", dto.getRefreshToken());
    }

    @Test
    void logoutRequest_constructorCompleto() {
        LogoutRequest dto = new LogoutRequest("access-abc", "refresh-xyz");
        assertEquals("access-abc", dto.getAccessToken());
        assertEquals("refresh-xyz", dto.getRefreshToken());
    }

    @Test
    void logoutRequest_equalsHashCodeToString() {
        LogoutRequest dto1 = new LogoutRequest("acc", "ref");
        LogoutRequest dto2 = new LogoutRequest("acc", "ref");
        LogoutRequest dto3 = new LogoutRequest("x", "y");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("string"));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("acc"));
    }

    @Test
    void logoutRequest_builder() {
        LogoutRequest dto = LogoutRequest.builder()
                .accessToken("acc")
                .refreshToken("ref")
                .build();
        assertEquals("acc", dto.getAccessToken());
    }

    // ══════════════════════════════════════════════
    // RefreshRequest
    // ══════════════════════════════════════════════

    @Test
    void refreshRequest_todosLosGettersSetters() {
        RefreshRequest dto = new RefreshRequest();
        dto.setRefreshToken("refresh-123");
        assertEquals("refresh-123", dto.getRefreshToken());
    }

    @Test
    void refreshRequest_constructorCompleto() {
        RefreshRequest dto = new RefreshRequest("token-xyz");
        assertEquals("token-xyz", dto.getRefreshToken());
    }

    @Test
    void refreshRequest_builder() {
        RefreshRequest dto = RefreshRequest.builder()
                .refreshToken("mi-refresh-token")
                .build();
        assertEquals("mi-refresh-token", dto.getRefreshToken());
    }

    @Test
    void refreshRequest_equalsHashCodeToString() {
        RefreshRequest dto1 = new RefreshRequest("tok");
        RefreshRequest dto2 = new RefreshRequest("tok");
        RefreshRequest dto3 = new RefreshRequest("otro");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("string"));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("tok"));
    }

    // ══════════════════════════════════════════════
    // SignosVitalesDto
    // ══════════════════════════════════════════════

    @Test
    void signosVitalesDto_todosLosGettersSetters() {
        LocalDateTime now = LocalDateTime.of(2024, 1, 15, 10, 30);
        FichaClinicaDto ficha = new FichaClinicaDto();
        ficha.setId(5L);

        SignosVitalesDto dto = new SignosVitalesDto();
        dto.setId(1L);
        dto.setPresion("120/80");
        dto.setFrecuencia(72);
        dto.setTemperatura(36.5);
        dto.setSaturacion(98);
        dto.setProfesional("Enf. López");
        dto.setFechaRegistro(now);
        dto.setFicha(ficha);

        assertEquals(1L, dto.getId());
        assertEquals("120/80", dto.getPresion());
        assertEquals(72, dto.getFrecuencia());
        assertEquals(36.5, dto.getTemperatura());
        assertEquals(98, dto.getSaturacion());
        assertEquals("Enf. López", dto.getProfesional());
        assertEquals(now, dto.getFechaRegistro());
        assertEquals(5L, dto.getFicha().getId());
    }

    @Test
    void signosVitalesDto_constructorCompleto() {
        LocalDateTime now = LocalDateTime.now();
        SignosVitalesDto dto = new SignosVitalesDto(1L, "130/85", 80, 37.0, 97, "Dr. Test", now, null);

        assertEquals(1L, dto.getId());
        assertEquals("130/85", dto.getPresion());
        assertNull(dto.getFicha());
    }

    @Test
    void signosVitalesDto_equalsHashCodeToString() {
        LocalDateTime now = LocalDateTime.now();
        SignosVitalesDto dto1 = new SignosVitalesDto(1L, "120/80", 72, 36.5, 98, "Enf.", now, null);
        SignosVitalesDto dto2 = new SignosVitalesDto(1L, "120/80", 72, 36.5, 98, "Enf.", now, null);
        SignosVitalesDto dto3 = new SignosVitalesDto(2L, "130/90", 80, 37.0, 95, "Dr.", now, null);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("string"));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("120/80"));
    }

    // ══════════════════════════════════════════════
    // FichaClinicaDto
    // ══════════════════════════════════════════════

    @Test
    void fichaClinicaDto_todosLosGettersSetters() {
        MedicamentoDto med = new MedicamentoDto();
        med.setNombre("Paracetamol");

        FichaClinicaDto dto = new FichaClinicaDto();
        dto.setId(1L);
        dto.setNombrePaciente("María González");
        dto.setRutPaciente("98765432-1");
        dto.setEdad(45);
        dto.setDiagnostico("Asma");
        dto.setAlergias("Aspirina");
        dto.setObservaciones("Control mensual");
        dto.setGenero("F");
        dto.setMedicamentos(List.of(med));

        assertEquals(1L, dto.getId());
        assertEquals("María González", dto.getNombrePaciente());
        assertEquals("98765432-1", dto.getRutPaciente());
        assertEquals(45, dto.getEdad());
        assertEquals("Asma", dto.getDiagnostico());
        assertEquals("Aspirina", dto.getAlergias());
        assertEquals("Control mensual", dto.getObservaciones());
        assertEquals("F", dto.getGenero());
        assertEquals(1, dto.getMedicamentos().size());
    }

    @Test
    void fichaClinicaDto_constructorCompleto() {
        FichaClinicaDto dto = new FichaClinicaDto(2L, "Carlos Ruiz", "55555555-5",
                30, "Gripe", "Ninguna", "Reposo", "M", List.of());
        assertEquals(2L, dto.getId());
        assertEquals("Carlos Ruiz", dto.getNombrePaciente());
    }

    @Test
    void fichaClinicaDto_equalsHashCodeToString() {
        FichaClinicaDto dto1 = new FichaClinicaDto(1L, "Juan", "11111111-1", 30,
                "Dx", "Al", "Obs", "M", List.of());
        FichaClinicaDto dto2 = new FichaClinicaDto(1L, "Juan", "11111111-1", 30,
                "Dx", "Al", "Obs", "M", List.of());
        FichaClinicaDto dto3 = new FichaClinicaDto(2L, "Pedro", "22222222-2", 25,
                "Otra", null, null, "M", null);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(99));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("Juan"));
    }

    // ══════════════════════════════════════════════
    // MedicamentoDto
    // ══════════════════════════════════════════════

    @Test
    void medicamentoDto_todosLosGettersSetters() {
        LocalDateTime now = LocalDateTime.now();
        MedicamentoDto dto = new MedicamentoDto();
        dto.setId(1L);
        dto.setNombre("Ibuprofeno");
        dto.setDosis("400mg");
        dto.setFrecuencia("Cada 8 horas");
        dto.setObservaciones("Con comida");
        dto.setDiasTratamiento(7);
        dto.setFechaRegistro(now);
        dto.setProfesional("Dr. Soto");
        dto.setFicha(5L);

        assertEquals(1L, dto.getId());
        assertEquals("Ibuprofeno", dto.getNombre());
        assertEquals("400mg", dto.getDosis());
        assertEquals("Cada 8 horas", dto.getFrecuencia());
        assertEquals("Con comida", dto.getObservaciones());
        assertEquals(7, dto.getDiasTratamiento());
        assertEquals(now, dto.getFechaRegistro());
        assertEquals("Dr. Soto", dto.getProfesional());
        assertEquals(5L, dto.getFicha());
    }

    @Test
    void medicamentoDto_constructorCompleto() {
        LocalDateTime now = LocalDateTime.now();
        MedicamentoDto dto = new MedicamentoDto(1L, "Amoxicilina", "500mg", "Cada 12h",
                "Antes de comer", 10, now, "Dr. Pérez", 3L);
        assertEquals("Amoxicilina", dto.getNombre());
        assertEquals(10, dto.getDiasTratamiento());
    }

    @Test
    void medicamentoDto_equalsHashCodeToString() {
        LocalDateTime now = LocalDateTime.now();
        MedicamentoDto dto1 = new MedicamentoDto(1L, "Ibuprofeno", "400mg", "Cada 8h",
                "Con comida", 7, now, "Dr. A", 1L);
        MedicamentoDto dto2 = new MedicamentoDto(1L, "Ibuprofeno", "400mg", "Cada 8h",
                "Con comida", 7, now, "Dr. A", 1L);
        MedicamentoDto dto3 = new MedicamentoDto(2L, "Paracetamol", "500mg", "Cada 6h",
                null, 5, now, "Dr. B", 2L);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("otra"));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("Ibuprofeno"));
    }

    // ══════════════════════════════════════════════
    // EvolucionClinicaDto
    // ══════════════════════════════════════════════

    @Test
    void evolucionClinicaDto_todosLosGettersSetters() {
        LocalDateTime now = LocalDateTime.now();
        EvolucionClinicaDto dto = new EvolucionClinicaDto();
        dto.setId(1L);
        dto.setFechaRegistro(now);
        dto.setProfesional("Dr. Ramírez");
        dto.setDescripcion("Evolución favorable");
        dto.setObservaciones("Sin complicaciones");
        dto.setPacienteId(10L);

        assertEquals(1L, dto.getId());
        assertEquals(now, dto.getFechaRegistro());
        assertEquals("Dr. Ramírez", dto.getProfesional());
        assertEquals("Evolución favorable", dto.getDescripcion());
        assertEquals("Sin complicaciones", dto.getObservaciones());
        assertEquals(10L, dto.getPacienteId());
    }

    @Test
    void evolucionClinicaDto_equalsHashCodeToString() {
        LocalDateTime now = LocalDateTime.now();
        EvolucionClinicaDto dto1 = new EvolucionClinicaDto();
        dto1.setId(1L);
        dto1.setDescripcion("Evolución");
        dto1.setProfesional("Dr. A");
        dto1.setFechaRegistro(now);
        dto1.setObservaciones("Obs");
        dto1.setPacienteId(5L);

        EvolucionClinicaDto dto2 = new EvolucionClinicaDto();
        dto2.setId(1L);
        dto2.setDescripcion("Evolución");
        dto2.setProfesional("Dr. A");
        dto2.setFechaRegistro(now);
        dto2.setObservaciones("Obs");
        dto2.setPacienteId(5L);

        EvolucionClinicaDto dto3 = new EvolucionClinicaDto();
        dto3.setId(2L);
        dto3.setDescripcion("Diferente");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(100));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("Dr. A"));
    }

    // ══════════════════════════════════════════════
    // ExamenClinicoDto
    // ══════════════════════════════════════════════

    @Test
    void examenClinicoDto_todosLosGettersSetters() {
        LocalDateTime now = LocalDateTime.now();
        ExamenClinicoDto dto = new ExamenClinicoDto();
        dto.setId(1L);
        dto.setNombre("Hemograma");
        dto.setFechaRegistro(now);
        dto.setEstado("Pendiente");
        dto.setProfesional("Dra. Flores");
        dto.setObservacion("Urgente");
        dto.setResultado("Normal");
        dto.setFicha(5L);

        assertEquals(1L, dto.getId());
        assertEquals("Hemograma", dto.getNombre());
        assertEquals(now, dto.getFechaRegistro());
        assertEquals("Pendiente", dto.getEstado());
        assertEquals("Dra. Flores", dto.getProfesional());
        assertEquals("Urgente", dto.getObservacion());
        assertEquals("Normal", dto.getResultado());
        assertEquals(5L, dto.getFicha());
    }

    @Test
    void examenClinicoDto_constructorCompleto() {
        LocalDateTime now = LocalDateTime.now();
        ExamenClinicoDto dto = new ExamenClinicoDto(2L, "Radiografía", now,
                "Completado", "Dr. Vega", "Sin hallazgos", "Normal", 3L);
        assertEquals(2L, dto.getId());
        assertEquals("Radiografía", dto.getNombre());
    }

    @Test
    void examenClinicoDto_equalsHashCodeToString() {
        LocalDateTime now = LocalDateTime.now();
        ExamenClinicoDto dto1 = new ExamenClinicoDto(1L, "Hemograma", now,
                "Pendiente", "Dr. A", "Obs", "Normal", 1L);
        ExamenClinicoDto dto2 = new ExamenClinicoDto(1L, "Hemograma", now,
                "Pendiente", "Dr. A", "Obs", "Normal", 1L);
        ExamenClinicoDto dto3 = new ExamenClinicoDto(2L, "Rx", now,
                "Completado", "Dr. B", null, "Alterado", 2L);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("string"));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("Hemograma"));
    }

    // ══════════════════════════════════════════════
    // ControlMedicoDto
    // ══════════════════════════════════════════════

    @Test
    void controlMedicoDto_todosLosGettersSetters() {
        ControlMedicoDto dto = new ControlMedicoDto();
        dto.setId(1L);
        dto.setFecha("2024-03-15");
        dto.setDiagnostico("Hipertensión controlada");
        dto.setObservaciones("Continuar tratamiento");
        dto.setPacienteId(7L);

        assertEquals(1L, dto.getId());
        assertEquals("2024-03-15", dto.getFecha());
        assertEquals("Hipertensión controlada", dto.getDiagnostico());
        assertEquals("Continuar tratamiento", dto.getObservaciones());
        assertEquals(7L, dto.getPacienteId());
    }

    @Test
    void controlMedicoDto_equalsHashCodeToString() {
        ControlMedicoDto dto1 = new ControlMedicoDto();
        dto1.setId(1L);
        dto1.setFecha("2024-01-01");
        dto1.setDiagnostico("Gripe");
        dto1.setObservaciones("Reposo");
        dto1.setPacienteId(3L);

        ControlMedicoDto dto2 = new ControlMedicoDto();
        dto2.setId(1L);
        dto2.setFecha("2024-01-01");
        dto2.setDiagnostico("Gripe");
        dto2.setObservaciones("Reposo");
        dto2.setPacienteId(3L);

        ControlMedicoDto dto3 = new ControlMedicoDto();
        dto3.setId(2L);
        dto3.setDiagnostico("Asma");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(true));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("Gripe"));
    }

    // ══════════════════════════════════════════════
    // IndicacionMedicaDto
    // ══════════════════════════════════════════════

    @Test
    void indicacionMedicaDto_todosLosGettersSetters() {
        LocalDateTime now = LocalDateTime.now();
        FichaClinicaDto ficha = new FichaClinicaDto();
        ficha.setId(3L);

        IndicacionMedicaDto dto = new IndicacionMedicaDto();
        dto.setId(1L);
        dto.setFechaRegistro(now);
        dto.setProfesional("Dr. Castro");
        dto.setIndicacion("Reposo absoluto 3 días");
        dto.setFicha(ficha);

        assertEquals(1L, dto.getId());
        assertEquals(now, dto.getFechaRegistro());
        assertEquals("Dr. Castro", dto.getProfesional());
        assertEquals("Reposo absoluto 3 días", dto.getIndicacion());
        assertEquals(3L, dto.getFicha().getId());
    }

    @Test
    void indicacionMedicaDto_constructorCompleto() {
        LocalDateTime now = LocalDateTime.now();
        FichaClinicaDto ficha = new FichaClinicaDto();
        IndicacionMedicaDto dto = new IndicacionMedicaDto(1L, now, "Enf. Torres",
                "Dieta blanda", ficha);
        assertEquals("Enf. Torres", dto.getProfesional());
    }

    @Test
    void indicacionMedicaDto_equalsHashCodeToString() {
        LocalDateTime now = LocalDateTime.now();

        IndicacionMedicaDto dto1 = new IndicacionMedicaDto();
        dto1.setId(1L);
        dto1.setIndicacion("Reposo");
        dto1.setProfesional("Dr. A");
        dto1.setFechaRegistro(now);

        IndicacionMedicaDto dto2 = new IndicacionMedicaDto();
        dto2.setId(1L);
        dto2.setIndicacion("Reposo");
        dto2.setProfesional("Dr. A");
        dto2.setFechaRegistro(now);

        IndicacionMedicaDto dto3 = new IndicacionMedicaDto();
        dto3.setId(2L);
        dto3.setIndicacion("Dieta");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(1.0));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("Reposo"));
    }

    // ══════════════════════════════════════════════
    // UserInfo
    // ══════════════════════════════════════════════

    @Test
    void userInfo_todosLosGettersSetters() {
        UserInfo dto = new UserInfo();
        dto.setId(1L);
        dto.setUsername("jperez");
        dto.setEmail("jperez@hospital.cl");
        dto.setNombreCompleto("Juan Perez");
        dto.setTipoUsuario("PROFESIONAL");
        dto.setRoles(List.of("ROLE_PROFESIONAL"));

        assertEquals(1L, dto.getId());
        assertEquals("jperez", dto.getUsername());
        assertEquals("jperez@hospital.cl", dto.getEmail());
        assertEquals("Juan Perez", dto.getNombreCompleto());
        assertEquals("PROFESIONAL", dto.getTipoUsuario());
        assertEquals(1, dto.getRoles().size());
    }

    @Test
    void userInfo_constructorCompleto() {
        List<String> roles = List.of("ROLE_ADMIN");
        UserInfo dto = new UserInfo(1L, "admin", "admin@test.com",
                "Administrador", "ADMIN", roles);
        assertEquals("admin", dto.getUsername());
        assertEquals(roles, dto.getRoles());
    }

    @Test
    void userInfo_equalsHashCodeToString() {
        List<String> roles = List.of("ROLE_USER");
        UserInfo dto1 = new UserInfo(1L, "usr", "a@b.cl", "Usr", "PAC", roles);
        UserInfo dto2 = new UserInfo(1L, "usr", "a@b.cl", "Usr", "PAC", roles);
        UserInfo dto3 = new UserInfo(2L, "otro", "x@y.cl", "Otro", "ADM", List.of());

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(List.of()));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("usr"));
    }

    // ══════════════════════════════════════════════
    // RegisterResponse
    // ══════════════════════════════════════════════

    @Test
    void registerResponse_todosLosGettersSetters() {
        UserInfo userInfo = new UserInfo(1L, "nuevo", "n@n.cl", "Nuevo", "PAC", List.of());

        RegisterResponse dto = new RegisterResponse();
        dto.setAccessToken("access-123");
        dto.setRefreshToken("refresh-456");
        dto.setTokenType("Bearer");
        dto.setExpiresIn(3600);
        dto.setUserInfo(userInfo);
        dto.setMessage("Registro exitoso");

        assertEquals("access-123", dto.getAccessToken());
        assertEquals("refresh-456", dto.getRefreshToken());
        assertEquals("Bearer", dto.getTokenType());
        assertEquals(3600, dto.getExpiresIn());
        assertEquals("nuevo", dto.getUserInfo().getUsername());
        assertEquals("Registro exitoso", dto.getMessage());
    }

    @Test
    void registerResponse_constructorCompleto() {
        UserInfo userInfo = new UserInfo();
        RegisterResponse dto = new RegisterResponse("acc", "ref", "Bearer", 7200, userInfo, "OK");
        assertEquals("acc", dto.getAccessToken());
        assertEquals(7200, dto.getExpiresIn());
    }

    @Test
    void registerResponse_equalsHashCodeToString() {
        UserInfo u = new UserInfo();
        RegisterResponse dto1 = new RegisterResponse("acc", "ref", "Bearer", 3600, u, "OK");
        RegisterResponse dto2 = new RegisterResponse("acc", "ref", "Bearer", 3600, u, "OK");
        RegisterResponse dto3 = new RegisterResponse("x", "y", "Basic", 0, null, "Error");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(0));

        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("Bearer"));
    }

    // ══════════════════════════════════════════════
    // RegisterRequest
    // ══════════════════════════════════════════════

    private RegisterRequest crearRegisterRequestCompleto() {
        RegisterRequest dto = new RegisterRequest();
        dto.setUsername("test.user");
        dto.setPassword("Password1!");
        dto.setEmail("test@hospital.cl");
        dto.setTipoUsuario("PROFESIONAL");
        dto.setNombres("Pedro");
        dto.setApellidos("Castro");
        dto.setTipoDocumento("RUT");
        dto.setNumeroDocumento("11222333-4");
        dto.setFechaNacimiento(LocalDate.of(1985, 6, 20));
        dto.setGenero("M");
        dto.setTelefono("+56900000001");
        dto.setDireccion("Calle Falsa 123");
        dto.setRoles(List.of("ROLE_PROFESIONAL"));
        dto.setAceptaTerminos(true);
        dto.setVersionTerminos(2);
        dto.setNumeroLicencia("LIC-999");
        dto.setProfesion("Médico");
        dto.setEspecialidad("Cardiología");
        dto.setSubespecialidad("Intervencionista");
        dto.setUniversidad("USACH");
        dto.setAnioGraduacion(2010);
        dto.setExperienciaAnios(14);
        dto.setInstitucion("Hospital X");
        dto.setHorasSemanales("40h/semana");
        dto.setPacientesRuts(List.of("12345678-9", "98765432-1"));
        dto.setHistoriaClinica("HC-123");
        dto.setGrupoSanguineo("A");
        dto.setFactorRh("+");
        dto.setAlergias("Penicilina");
        dto.setEnfermedadesCronicas("HTA");
        dto.setMedicamentosActuales("Losartán");
        dto.setContactoEmergencia("María Castro");
        dto.setTelefonoEmergencia("+56911111111");
        dto.setPrevision("Fonasa");
        return dto;
    }

    @Test
    void registerRequest_todosLosGettersSetters() {
        RegisterRequest dto = crearRegisterRequestCompleto();

        assertEquals("test.user", dto.getUsername());
        assertEquals("Password1!", dto.getPassword());
        assertEquals("test@hospital.cl", dto.getEmail());
        assertEquals("PROFESIONAL", dto.getTipoUsuario());
        assertEquals("Pedro", dto.getNombres());
        assertEquals("Castro", dto.getApellidos());
        assertEquals("RUT", dto.getTipoDocumento());
        assertEquals("11222333-4", dto.getNumeroDocumento());
        assertEquals(LocalDate.of(1985, 6, 20), dto.getFechaNacimiento());
        assertEquals("M", dto.getGenero());
        assertEquals("+56900000001", dto.getTelefono());
        assertEquals("Calle Falsa 123", dto.getDireccion());
        assertEquals(1, dto.getRoles().size());
        assertTrue(dto.getAceptaTerminos());
        assertEquals(2, dto.getVersionTerminos());
        assertEquals("LIC-999", dto.getNumeroLicencia());
        assertEquals("Médico", dto.getProfesion());
        assertEquals("Cardiología", dto.getEspecialidad());
        assertEquals("Intervencionista", dto.getSubespecialidad());
        assertEquals("USACH", dto.getUniversidad());
        assertEquals(2010, dto.getAnioGraduacion());
        assertEquals(14, dto.getExperienciaAnios());
        assertEquals("Hospital X", dto.getInstitucion());
        assertEquals("40h/semana", dto.getHorasSemanales());
        assertEquals(2, dto.getPacientesRuts().size());
        assertEquals("HC-123", dto.getHistoriaClinica());
        assertEquals("A", dto.getGrupoSanguineo());
        assertEquals("+", dto.getFactorRh());
        assertEquals("Penicilina", dto.getAlergias());
        assertEquals("HTA", dto.getEnfermedadesCronicas());
        assertEquals("Losartán", dto.getMedicamentosActuales());
        assertEquals("María Castro", dto.getContactoEmergencia());
        assertEquals("+56911111111", dto.getTelefonoEmergencia());
        assertEquals("Fonasa", dto.getPrevision());
    }

    @Test
    void registerRequest_toString() {
        RegisterRequest dto = crearRegisterRequestCompleto();
        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("test.user"));
        assertTrue(str.contains("Cardiología"));
    }

    @Test
    void registerRequest_equalsHashCode() {
        RegisterRequest dto1 = crearRegisterRequestCompleto();
        RegisterRequest dto2 = crearRegisterRequestCompleto();
        RegisterRequest dto3 = new RegisterRequest();
        dto3.setUsername("otro");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("string"));
    }

    @Test
    void registerRequest_builder() {
        RegisterRequest dto = RegisterRequest.builder()
                .username("nuevo.usuario")
                .password("Password1!")
                .email("nuevo@hospital.cl")
                .tipoUsuario("PACIENTE")
                .nombres("Ana")
                .apellidos("López")
                .tipoDocumento("RUT")
                .numeroDocumento("99999999-9")
                .genero("F")
                .telefono("+56922222222")
                .direccion("Av. Siempre Viva")
                .aceptaTerminos(true)
                .versionTerminos(1)
                .historiaClinica("HC-456")
                .grupoSanguineo("O")
                .factorRh("-")
                .alergias("Ninguna")
                .enfermedadesCronicas("Ninguna")
                .medicamentosActuales("Ninguno")
                .contactoEmergencia("Padre")
                .telefonoEmergencia("+56933333333")
                .prevision("Isapre")
                .build();

        assertEquals("nuevo.usuario", dto.getUsername());
        assertEquals("Ana", dto.getNombres());
        assertEquals("O", dto.getGrupoSanguineo());
        assertNotNull(dto.getRoles()); // default @Builder.Default
    }

    @Test
    void registerRequest_constructorVacio() {
        RegisterRequest dto = new RegisterRequest();
        assertNotNull(dto);
        assertNull(dto.getUsername());
    }
}
