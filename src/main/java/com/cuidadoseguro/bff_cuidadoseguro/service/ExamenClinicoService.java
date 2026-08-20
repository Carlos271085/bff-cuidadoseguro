package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.ExamenClinicoDto;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamenClinicoService {

        private final RestTemplate restTemplate;

        @Value("${datosmedicos.url}")
        private String datosMedicosUrl;

        private final String BASE_PATH = "/examenes";

        private HttpHeaders buildHeaders(String token) {

                HttpHeaders headers = new HttpHeaders();

                headers.setBearerAuth(token);

                headers.setContentType(MediaType.APPLICATION_JSON);

                headers.setAccept(List.of(MediaType.APPLICATION_JSON));

                return headers;
        }

        public List<ExamenClinicoDto> listarTodos(String token) {

                HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));

                ResponseEntity<ExamenClinicoDto[]> response = restTemplate.exchange(
                                datosMedicosUrl + BASE_PATH,
                                HttpMethod.GET,
                                entity,
                                ExamenClinicoDto[].class);

                return Arrays.asList(response.getBody());
        }

        public ExamenClinicoDto actualizar(
                        String token,
                        Long id,
                        ExamenClinicoDto examen) {

                HttpEntity<ExamenClinicoDto> entity = new HttpEntity<>(examen, buildHeaders(token));

                try {

                        ResponseEntity<ExamenClinicoDto> response = restTemplate.exchange(
                                        datosMedicosUrl + BASE_PATH + "/" + id,
                                        HttpMethod.PUT,
                                        entity,
                                        ExamenClinicoDto.class);

                        return response.getBody();

                } catch (Exception e) {

                        e.printStackTrace();

                        throw e;
                }
        }

        public ExamenClinicoDto guardar(
                        String token,
                        ExamenClinicoDto examen) {

                HttpEntity<ExamenClinicoDto> entity = new HttpEntity<>(examen, buildHeaders(token));

                ResponseEntity<ExamenClinicoDto> response = restTemplate.exchange(
                                datosMedicosUrl + BASE_PATH,
                                HttpMethod.POST,
                                entity,
                                ExamenClinicoDto.class);

                return response.getBody();
        }

        public ExamenClinicoDto buscarPorId(
                        String token,
                        Long id) {

                HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));

                ResponseEntity<ExamenClinicoDto> response = restTemplate.exchange(
                                datosMedicosUrl + BASE_PATH + "/" + id,
                                HttpMethod.GET,
                                entity,
                                ExamenClinicoDto.class);

                return response.getBody();
        }

        public void eliminar(
                        String token,
                        Long id) {

                HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));

                restTemplate.exchange(
                                datosMedicosUrl + BASE_PATH + "/" + id,
                                HttpMethod.DELETE,
                                entity,
                                Void.class);
        }
}