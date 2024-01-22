package com.project.parkinglot.model.dto.request.price;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceCreateRequest {
    @NotNull(message = "lowerBound boş olamaz")
    @Min(value = 0, message = "lowerBound en az 0 olmalıdır")
    @Max(value = 24, message = "lowerBound en fazla 24 olmalıdır")
    private Integer lowerBound;

    @NotNull(message = "upperBound boş olamaz")
    @Min(value = 0, message = "upperBound en az 0 olmalıdır")
    @Max(value = 24, message = "upperBound en fazla 24 olmalıdır")
    private Integer upperBound;

    @NotNull
    private BigDecimal cost;
}
