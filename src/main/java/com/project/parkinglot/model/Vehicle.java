package com.project.parkinglot.model;

import com.project.parkinglot.common.model.BaseDomainModel;
import com.project.parkinglot.model.enums.VehicleType;
import com.project.parkinglot.security.model.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle extends BaseDomainModel {

    private String id;
    private String licensePlate;
    private VehicleType vehicleType;
    private List<Park> parkList;
    private User user;

}
