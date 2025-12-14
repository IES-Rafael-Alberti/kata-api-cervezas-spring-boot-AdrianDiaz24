package com.kata.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kata API Cervezas")
                        .version("1.0.0")
                        .description("API REST CRUD para gestión de cervezas, cervecerías, categorías y estilos de cerveza")
                        .contact(new Contact()
                                .name("Soporte API")
                                .email("support@kata-api.com")
                                .url("https://kata-api.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

