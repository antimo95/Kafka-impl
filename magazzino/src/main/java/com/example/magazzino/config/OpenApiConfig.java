package com.example.magazzino.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI magazzinoOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Magazzino API")
                .description("API per la gestione delle scorte di magazzino")
                .version("v1"));
    }
}
