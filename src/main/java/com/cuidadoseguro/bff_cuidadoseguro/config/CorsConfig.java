package com.cuidadoseguro.bff_cuidadoseguro.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * CORS para consumidores directos del BFF en desarrollo local.
 * En produccion el navegador solo habla con AWS API Gateway, que es quien
 * resuelve el preflight; por eso la lista de origenes se deja vacia.
 */
@Configuration
public class CorsConfig {

        @Value("${cors.allowed-origins:}")
        private String allowedOrigins;

        @Bean
        public CorsWebFilter corsWebFilter() {

                CorsConfiguration config = new CorsConfiguration();

                List<String> origins = Arrays.stream(allowedOrigins.split(","))
                                .map(String::trim)
                                .filter(origin -> !origin.isEmpty())
                                .toList();

                config.setAllowedOrigins(origins);
                config.setAllowCredentials(!origins.isEmpty());

                config.setAllowedMethods(List.of(
                                "GET",
                                "POST",
                                "PUT",
                                "PATCH",
                                "DELETE",
                                "OPTIONS"));

                config.setAllowedHeaders(List.of(
                                "Authorization",
                                "Content-Type"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);

                return new CorsWebFilter(source);
        }
}
