package com.project.parkinglot.model.dto.response.vehicle;

import com.project.parkinglot.model.enums.VehicleType;
import lombok.*;

/**
 * A response class named {@link VehicleCheckInResponse} representing the vehicle check-in response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleCheckInResponse {

    private String licensePlate;
    private VehicleType vehicleType;
}
