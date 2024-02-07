package com.project.parkinglot.model.dto.request.parking_area;

import com.project.parkinglot.model.dto.request.priceList.PriceListCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingAreaCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotNull
    @Min(value = 0)
    private Integer capacity;

    @NotBlank
    private String city;

    @Valid
    @NotNull
    private PriceListCreateRequest priceListRequest;

}
