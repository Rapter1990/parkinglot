package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.entity.PriceListEntity;

import java.util.UUID;

public class ParkingAreaEntityBuilder extends BaseBuilder<ParkingAreaEntity> {

    public ParkingAreaEntityBuilder() {
        super(ParkingAreaEntity.class);
    }

    public ParkingAreaEntityBuilder withValidFields() {
        final Faker faker = new Faker();
        return this
                .witId(UUID.randomUUID().toString())
                .withName(faker.name().name())
                .withLocation(faker.address().fullAddress())
                .withCapacity(faker.number().numberBetween(1, 100))
                .withCity(faker.address().city())
                .withPriceListEntity(new PriceListEntityBuilder().withValidFields().build());
    }

    public ParkingAreaEntityBuilder witId(
            final String id
    ){
        data.setId(id);
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

    public ParkingAreaEntityBuilder withPriceListEntity(
            final PriceListEntity priceListEntity
    ) {
        data.setPriceListEntity(priceListEntity);
        return this;
    }

}
