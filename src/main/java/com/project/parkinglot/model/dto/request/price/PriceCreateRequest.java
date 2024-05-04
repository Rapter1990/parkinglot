package com.project.parkinglot.model.dto.request.price;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * A request class named {@link PriceCreateRequest} representing the creation of a price.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceCreateRequest {

    @NotNull(message = "lowerBound cannot be null")
    @Min(value = 0, message = "lowerBound must be at least 0")
    @Max(value = 24, message = "lowerBound must be at most 24")
    private Integer lowerBound;

    @NotNull(message = "upperBound cannot be null")
    @Min(value = 0, message = "upperBound must be at least 0")
    @Max(value = 24, message = "upperBound must be at most 24")
    private Integer upperBound;

    @NotNull(message = "cost cannot be null")
    private BigDecimal cost;

}
