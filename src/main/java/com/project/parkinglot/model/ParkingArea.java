package com.project.parkinglot.model;

import com.project.parkinglot.common.model.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParkingArea extends BaseDomainModel {

    private String id;
    private String name;
    private String location;
    private Integer capacity;
    private String city;
    private PriceList priceList;
    private List<Park> parkList;
    private List<DailyIncome> dailyIncomeList;

}
