package com.project.parkinglot.model.dto.response;

import com.project.parkinglot.common.model.BaseDomainModel;
import com.project.parkinglot.model.dto.response.vehicle.VehicleCheckOutResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A response class named {@link ParkDetailResponse} representing the park detail response.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParkDetailResponse extends BaseDomainModel {

    private String parkingAreaName;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private BigDecimal totalCost;

}
