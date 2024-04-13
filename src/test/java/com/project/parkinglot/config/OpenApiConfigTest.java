package com.project.parkinglot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void openApiInfo() {

        // Given
        OpenAPIDefinition openAPIDefinition = OpenApiConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Then
        assertEquals("1.0.0", openAPIDefinition.info().version());
        assertEquals("Parking Lot App API", openAPIDefinition.info().title());
        assertEquals("Case Study - Parking Lot (Spring Boot, Spring Security , Mysql, JUnit, Integration Test, Docker, Test Container, AOP, Prometheus, Grafana) ", openAPIDefinition.info().description());

    }

    @Test
    void securityScheme() {

        // Given
        SecurityScheme securityScheme = OpenApiConfig.class.getAnnotation(SecurityScheme.class);

        // Then
        assertEquals("bearerAuth", securityScheme.name());
        assertEquals("JWT Token", securityScheme.description());
        assertEquals("bearer", securityScheme.scheme());
        assertEquals(SecuritySchemeType.HTTP, securityScheme.type());
        assertEquals("JWT", securityScheme.bearerFormat());
        assertEquals(SecuritySchemeIn.HEADER, securityScheme.in());

    }

    @Test
    void contactInfo() {

        // Given
        Info info = OpenApiConfig.class.getAnnotation(OpenAPIDefinition.class).info();
        Contact contact = info.contact();

        // Then
        assertEquals("Sercan Noyan Germiyanoğlu | Mehmet Şeymus Yüzen | Harun Yusuf Ekşioğlu | Muhammet Oğuzhan Aydoğan", contact.name());
        assertEquals("https://github.com/Rapter1990/parkinglot/", contact.url());

    }

}
