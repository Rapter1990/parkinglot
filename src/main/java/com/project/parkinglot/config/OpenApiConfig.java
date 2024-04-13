package com.project.parkinglot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

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
public class OpenApiConfig {

}
