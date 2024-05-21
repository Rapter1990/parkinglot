package com.project.parkinglot.model.dto.response.parkingarea;

import lombok.*;

import java.math.BigDecimal;

/**
 * A response class named {@link ParkingAreaIncomeResponse} representing the parking area income response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingAreaIncomeResponse {


    private String name;
    private BigDecimal income;
}
