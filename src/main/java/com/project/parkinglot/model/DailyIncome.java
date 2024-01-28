package com.project.parkinglot.model;

import com.project.parkinglot.common.model.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DailyIncome extends BaseDomainModel {

    private String id;
    private LocalDate date;
    private BigDecimal income;
    private ParkingArea parkingArea;

}
