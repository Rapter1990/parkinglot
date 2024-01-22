package com.project.parkinglot.model.dto.request.priceList;

import com.project.parkinglot.model.dto.request.price.PriceCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceListCreateRequest {
    @NotBlank
    private String name;

    @Valid
    private List<PriceCreateRequest> prices;
}
