package com.project.parkinglot.model.dto.request.park;

import com.project.parkinglot.model.dto.request.vehicle.VehicleRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * A request class named {@link ParkCheckInRequest} representing the check-in request for a vehicle at a parking area.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkCheckInRequest {

    @NotNull(message = "Parking area id must be required")
    private String parkingAreaId;

    @NotNull(message = "Vehicle must be required")
    @Valid
    private VehicleRequest vehicle;
}
