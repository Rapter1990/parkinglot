package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.entity.PriceEntity;
import com.project.parkinglot.model.entity.PriceListEntity;

import java.util.List;

public class PriceListEntityBuilder extends BaseBuilder<PriceListEntity> {

    public PriceListEntityBuilder() {
        super(PriceListEntity.class);
    }

    public PriceListEntityBuilder withValidFields() {
        final Faker faker = new Faker();
        return this.withName(faker.name().name())
                .withPriceEntities(List.of(new PriceEntityBuilder().withValidFields().build()));

    }

    public PriceListEntityBuilder withName(
            final String name
    ) {
        data.setName(name);
        return this;
    }

    public PriceListEntityBuilder withPriceEntities(final List<PriceEntity> priceEntities) {
        data.setPriceEntities(priceEntities);
        return this;
    }

}
