package com.project.parkinglot.model.dto.request.parking_area;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A request class named {@link ParkingAreaUpdateRequest} representing the creation of a parking area.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingAreaUpdateRequest {

    @NotNull
    @Min(value = 0, message = "value of capacity should be bigger than zero")
    @Max(value = 24, message = "UpperBound must be at most 24")
    private Integer capacity;

}
