package com.cuidadoseguro.bff_cuidadoseguro.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cuidadoseguro.bff_cuidadoseguro.dto.AntropometriaDto;

@Service
public class AntropometriaService {

        private final RestTemplate restTemplate;

        @Value("${datosmedicos.url}")
        private String datosMedicosUrl;

        public AntropometriaService(
                        RestTemplate restTemplate) {

                this.restTemplate = restTemplate;
        }

        public AntropometriaDto guardar(
                        Long fichaId,
                        AntropometriaDto dto,
                        String token) {

                HttpHeaders headers = new HttpHeaders();

                headers.setBearerAuth(token);

                headers.setContentType(MediaType.APPLICATION_JSON);

                headers.setAccept(List.of(MediaType.APPLICATION_JSON));

                HttpEntity<AntropometriaDto> entity = new HttpEntity<>(dto, headers);

                ResponseEntity<AntropometriaDto> response = restTemplate.exchange(
                                datosMedicosUrl +
                                                "/antropometrias/" + fichaId,
                                HttpMethod.POST,
                                entity,
                                AntropometriaDto.class);

                return response.getBody();
        }

        public List<AntropometriaDto> listar(
                        Long fichaId,
                        String token) {

                HttpHeaders headers = new HttpHeaders();

                headers.setBearerAuth(token);

                HttpEntity<Void> entity = new HttpEntity<>(headers);

                ResponseEntity<AntropometriaDto[]> response = restTemplate.exchange(
                                datosMedicosUrl +
                                                "/antropometrias/" + fichaId,
                                HttpMethod.GET,
                                entity,
                                AntropometriaDto[].class);

                return Arrays.asList(response.getBody());
        }
}
