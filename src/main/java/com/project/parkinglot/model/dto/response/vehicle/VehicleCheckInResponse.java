package com.project.parkinglot.model.dto.response.vehicle;

import com.project.parkinglot.model.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleCheckInResponse {

    private String licensePlate;
    private VehicleType vehicleType;
}
