package com.devcaiqueoliveira.mercatopdvsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mercato API")
                        .description("API para gestão de ponto de venda.")
                        .contact(new Contact()
                                .name("Caique Oliveira")
                                .email("devcaiqueoliveira@gmail.com")
                        )
                        .version("1.0.0")
                );
    }
}