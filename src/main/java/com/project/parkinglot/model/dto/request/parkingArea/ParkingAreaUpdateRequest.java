package com.project.parkinglot.model.dto.request.parkingArea;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingAreaUpdateRequest {

    @NotNull
    @Min(value = 0,message = "value of capacity should be bigger than zero")
    @Max(value = 24,message = "UpperBound must be at most 24")
    private Integer capacity;

}
