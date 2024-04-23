package com.project.parkinglot.model.dto.request.park;

import com.project.parkinglot.model.dto.request.vehicle.VehicleRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkCheckOutRequest {

    @NotNull(message = "Parking area id must be required")
    private String parkingAreaId;

    @NotNull(message = "Vehicle must be required")
    @Valid
    private VehicleRequest vehicleRequest;

}
