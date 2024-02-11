package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.entity.PriceListEntity;

public class PriceListEntityBuilder extends BaseBuilder<PriceListEntity> {

    public PriceListEntityBuilder() {
        super(PriceListEntity.class);
    }

    public PriceListEntityBuilder withValidFields() {
        Faker faker = new Faker();
        return this.withName(faker.name().name());
    }

    public PriceListEntityBuilder withName(String name) {
        data.setName(name);
        return this;
    }

}
