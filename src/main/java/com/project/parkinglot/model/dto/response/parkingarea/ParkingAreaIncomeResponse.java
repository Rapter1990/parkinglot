package com.project.parkinglot.model.dto.response.parkingarea;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingAreaIncomeResponse {


    private String name;
    private BigDecimal income;
}
