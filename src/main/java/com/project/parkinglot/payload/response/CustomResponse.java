package com.project.parkinglot.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/**
 * Represents a custom response.
 * @param <T> the type of the response
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomResponse<T> {

    public CustomResponse(@Nullable T response, @Nonnull HttpStatus status) {
        this.response = response;
        this.httpStatus = status;
        this.isSuccess = status.is2xxSuccessful();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;

    private Boolean isSuccess;

    private HttpStatus httpStatus;

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    public static final CustomResponse<Void> SUCCESS = CustomResponse.<Void>builder()
            .isSuccess(true)
            .httpStatus(HttpStatus.OK)
            .build();


    /**
     * Creates a successful response with the given response body.
     *
     * @param response the response body
     * @param <E>      the type of the response body
     * @return a CustomResponse object
     */
    public static <E> CustomResponse<E> ok(E response) {
        return CustomResponse.<E>builder()
                .response(response)
                .isSuccess(true)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    /**
     * Creates a successful response with the given response body and HTTP status CREATED.
     *
     * @param response the response body
     * @param <E>      the type of the response body
     * @return a CustomResponse object
     */
    @ResponseStatus(HttpStatus.CREATED)
    public static <E> CustomResponse<E> created(E response) {
        return CustomResponse.<E>builder()
                .response(response)
                .isSuccess(true)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

}
