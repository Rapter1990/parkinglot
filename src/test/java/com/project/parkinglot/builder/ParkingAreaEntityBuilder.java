package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.entity.PriceListEntity;

public class ParkingAreaEntityBuilder extends BaseBuilder<ParkingAreaEntity> {

    public ParkingAreaEntityBuilder() {
        super(ParkingAreaEntity.class);
    }

    public ParkingAreaEntityBuilder withValidFields() {
        Faker faker = new Faker();
        return this
                .withName(faker.name().name())
                .withLocation(faker.address().fullAddress())
                .withCapacity(faker.number().numberBetween(1, 100))
                .withCity(faker.address().city())
                .withPriceListEntity(new PriceListEntityBuilder().withValidFields().build());
    }

    public ParkingAreaEntityBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public ParkingAreaEntityBuilder withLocation(String location) {
        data.setLocation(location);
        return this;
    }

    public ParkingAreaEntityBuilder withCapacity(Integer capacity) {
        data.setCapacity(capacity);
        return this;
    }

    public ParkingAreaEntityBuilder withCity(String city) {
        data.setCity(city);
        return this;
    }

    public ParkingAreaEntityBuilder withPriceListEntity(PriceListEntity priceListEntity) {
        data.setPriceListEntity(priceListEntity);
        return this;
    }

}
