package com.cuidadoseguro.bff_cuidadoseguro.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

        @Bean
        public CorsWebFilter corsWebFilter() {

                CorsConfiguration config = new CorsConfiguration();

                // IMPORTANTE: si usas cookies o Authorization con credentials
                config.setAllowCredentials(true);

                String origins = System.getenv().getOrDefault(
                                "CORS_ALLOWED_ORIGINS",
                                "*");

                // Permite múltiples origins desde env o wildcard
                if ("*".equals(origins)) {
                        config.setAllowedOriginPatterns(List.of("*"));
                } else {
                        config.setAllowedOrigins(Arrays.asList(origins.split(",")));
                }

                config.setAllowedMethods(List.of(
                                "GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "OPTIONS"));

                config.setAllowedHeaders(List.of("*"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);

                return new CorsWebFilter(source);
        }
}