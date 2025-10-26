package br.com.github.williiansilva51.todolist.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Todolist API").description("Todolist API").version("0.0.1-SNAPSHOT").termsOfService("""
                https://swagger.io/terms/""").license(new License().name("MIT").url("""
                https://springdoc.org""")).contact(new Contact().name("Willian").email("williansilva@alu.ufc.br")));
    }
}
