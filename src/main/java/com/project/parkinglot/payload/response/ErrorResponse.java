package com.project.parkinglot.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an error response named {@link ErrorResponse}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    private HttpStatus status;

    private Integer statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errorDetails;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}
