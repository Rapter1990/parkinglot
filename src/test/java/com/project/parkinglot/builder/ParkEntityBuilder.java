package com.project.parkinglot.builder;

import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.model.enums.ParkStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ParkEntityBuilder extends BaseBuilder<ParkEntity> {

    public ParkEntityBuilder() {
        super(ParkEntity.class);
    }

    public ParkEntityBuilder withValidFields() {
        return this
                .withId(UUID.randomUUID().toString())
                .withVehicleEntity(new VehicleEntityBuilder().withValidFields().build())
                .withParkStatus(ParkStatus.FULL);
    }

    public ParkEntityBuilder withId(final String id) {
        data.setId(id);
        return this;
    }

    public ParkEntityBuilder withVehicleEntity(final VehicleEntity vehicleEntity) {
        data.setVehicleEntity(vehicleEntity);
        return this;
    }

    public ParkEntityBuilder withParkingAreaEntity(final ParkingAreaEntity parkingAreaEntity) {
        data.setParkingAreaEntity(parkingAreaEntity);
        return this;
    }

    public ParkEntityBuilder withParkStatus(final ParkStatus parkStatus) {
        data.setParkStatus(parkStatus);
        return this;
    }

    public ParkEntityBuilder withCheckIn() {
        data.setCheckIn(LocalDateTime.now());
        return this;
    }

    public ParkEntityBuilder withCheckOut() {
        data.setCheckOut(LocalDateTime.now());
        return this;
    }

}

