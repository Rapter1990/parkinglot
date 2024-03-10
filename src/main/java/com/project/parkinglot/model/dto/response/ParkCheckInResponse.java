package com.project.parkinglot.model.dto.response;

import com.project.parkinglot.model.enums.ParkStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkCheckInResponse {

    private String parkingAreaId;
    private VehicleCheckInResponse vehicleCheckInResponse;
    private ParkStatus parkStatus;
    private LocalDateTime checkIn;
}
