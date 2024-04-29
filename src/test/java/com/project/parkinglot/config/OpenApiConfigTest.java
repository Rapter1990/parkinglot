package com.project.parkinglot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenApiConfigTest {

    @Test
    void openApiInfo() {

        // Given
        OpenAPIDefinition openAPIDefinition = OpenApiConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Then
        Assertions.assertEquals("1.0.0", openAPIDefinition.info().version());
        Assertions.assertEquals("Parking Lot App API", openAPIDefinition.info().title());
        Assertions.assertEquals("Case Study - Parking Lot (Spring Boot, Spring Security , Mysql, JUnit, Integration Test, Docker, Test Container, AOP, Prometheus, Grafana) ", openAPIDefinition.info().description());

    }

    @Test
    void securityScheme() {

        // Given
        SecurityScheme securityScheme = OpenApiConfig.class.getAnnotation(SecurityScheme.class);

        // Then
        Assertions.assertEquals("bearerAuth", securityScheme.name());
        Assertions.assertEquals("JWT Token", securityScheme.description());
        Assertions.assertEquals("bearer", securityScheme.scheme());
        Assertions.assertEquals(SecuritySchemeType.HTTP, securityScheme.type());
        Assertions.assertEquals("JWT", securityScheme.bearerFormat());
        Assertions.assertEquals(SecuritySchemeIn.HEADER, securityScheme.in());

    }

    @Test
    void contactInfo() {

        // Given
        Info info = OpenApiConfig.class.getAnnotation(OpenAPIDefinition.class).info();
        Contact contact = info.contact();

        // Then
        Assertions.assertEquals("Sercan Noyan Germiyanoğlu | Mehmet Şeymus Yüzen | Harun Yusuf Ekşioğlu | Muhammet Oğuzhan Aydoğan", contact.name());
        Assertions.assertEquals("https://github.com/Rapter1990/parkinglot/", contact.url());

    }

}
