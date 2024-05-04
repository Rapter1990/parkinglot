package com.project.parkinglot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Configuration class named {@link OpenApiConfig} for OpenAPI documentation.
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Sercan Noyan Germiyanoğlu | Mehmet Şeymus Yüzen | Harun Yusuf Ekşioğlu | Muhammet Oğuzhan Aydoğan",
                        url = "https://github.com/Rapter1990/parkinglot/"
                ),
                description = "Case Study - Parking Lot " +
                        "(Spring Boot, Spring Security , Mysql, JUnit, Integration Test, Docker, Test Container, AOP, Prometheus, Grafana) ",
                title = "Parking Lot App API",
                version = "1.0.0"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
