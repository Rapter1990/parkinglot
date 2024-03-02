package com.project.parkinglot.model.dto.request.Vehicle;

import com.project.parkinglot.model.enums.VehicleType;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleRequest {

    @Size(max = 10)
    private String licensePlate;

    private VehicleType vehicleType;

}
