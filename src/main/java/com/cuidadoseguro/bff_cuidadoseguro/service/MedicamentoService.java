package com.cuidadoseguro.bff_cuidadoseguro.service;

import com.cuidadoseguro.bff_cuidadoseguro.dto.MedicamentoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentoService {

    private final RestTemplate restTemplate;

    @Value("${datosmedicos.url}")
    private String datosMedicosUrl;

    private final String BASE_PATH = "/medicamentos";

    public List<MedicamentoDto> listar(String token) {

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(
                token.replace("Bearer ", "")
        );

        HttpEntity<Void> entity =
                new HttpEntity<>(headers);

        ResponseEntity<MedicamentoDto[]> response =
                restTemplate.exchange(
                        datosMedicosUrl + BASE_PATH,
                        HttpMethod.GET,
                        entity,
                        MedicamentoDto[].class
                );

        return response.getBody() != null
                ? Arrays.asList(response.getBody())
                : List.of();
    }

    public MedicamentoDto guardar(String token, MedicamentoDto medicamentoDto) {

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(
                token.replaceAll("Bearer ", "")
        );

        HttpEntity<MedicamentoDto> entity =
                new HttpEntity<>(medicamentoDto, headers);

        return restTemplate.exchange(
                datosMedicosUrl + BASE_PATH,
                HttpMethod.POST,
                entity,
                MedicamentoDto.class
        ).getBody();
    }

        public MedicamentoDto actualizar(String token, Long id, MedicamentoDto medicamentoDto) {
        
                HttpHeaders headers = new HttpHeaders();
        
                headers.setBearerAuth(
                        token.replaceAll("Bearer ", "")
                );
        
                HttpEntity<MedicamentoDto> entity =
                        new HttpEntity<>(medicamentoDto, headers);
        
                return restTemplate.exchange(
                        datosMedicosUrl + BASE_PATH + "/" + id,
                        HttpMethod.PUT,
                        entity,
                        MedicamentoDto.class
                ).getBody();
        }
}