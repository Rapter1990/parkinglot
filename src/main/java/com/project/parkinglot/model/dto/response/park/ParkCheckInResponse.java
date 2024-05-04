package com.project.parkinglot.model.dto.response.park;

import com.project.parkinglot.common.model.BaseDomainModel;
import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.dto.response.vehicle.VehicleCheckInResponse;
import com.project.parkinglot.model.enums.ParkStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * A response class named {@link ParkCheckInResponse} representing the check-in response.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParkCheckInResponse extends BaseDomainModel {

    private String parkingAreaId;
    private VehicleCheckInResponse vehicleCheckInResponse;
    private ParkStatus parkStatus;
    private LocalDateTime checkIn;
}
