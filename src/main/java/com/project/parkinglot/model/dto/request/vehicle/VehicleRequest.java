package com.project.parkinglot.model.dto.request.vehicle;

import com.project.parkinglot.model.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * A request class named {@link VehicleRequest} representing licensePlate and vehicleType.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequest {

    @NotBlank
    @Size(max = 10)
    private String licensePlate;

    private VehicleType vehicleType;

}
