package com.project.parkinglot.builder.model.entity;

import com.github.javafaker.Faker;
import com.project.parkinglot.builder.BaseBuilder;
import com.project.parkinglot.model.entity.ParkingAreaEntity;

import java.util.UUID;

public class ParkingAreaEntityBuilder extends BaseBuilder<ParkingAreaEntity> {
    public ParkingAreaEntityBuilder() {
        super(ParkingAreaEntity.class);
    }

    public ParkingAreaEntityBuilder withValidFields()
    {
        Faker faker = new Faker();
        return this.withId(UUID.randomUUID().toString())
                .withName(faker.name().name())
                .withLocation(faker.address().fullAddress())
                .withCapacity(faker.number().numberBetween(1,100))
                .withCity(faker.address().city());
    }

    public ParkingAreaEntityBuilder withId(
            final String parkingAreaId
    ) {
        data.setId(parkingAreaId);
        return this;
    }

    public ParkingAreaEntityBuilder withName(
            final String name
    ) {
        data.setName(name);
        return this;
    }

    public ParkingAreaEntityBuilder withLocation(
            final String location
    ) {
        data.setLocation(location);
        return this;
    }

    public ParkingAreaEntityBuilder withCapacity(
            final Integer capacity
    ) {
        data.setCapacity(capacity);
        return this;
    }

    public ParkingAreaEntityBuilder withCity(
            final String city
    ) {
        data.setCity(city);
        return this;
    }

    // TODO : PriceListEntityBuilder yazıldığında, withPriceListEntity adında metod eklenmeli.
    // TODO : ParkEntityBuilder yazıldığında, withParkEntities adında metod eklenmeli.
    // TODO : DailyIncomeEntityBuilder yazıldığında, withDailyIncomes adında metod eklenmeli.

    @Override
    public ParkingAreaEntity build() {
        return super.build();
    }
}
