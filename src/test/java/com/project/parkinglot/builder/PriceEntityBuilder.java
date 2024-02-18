package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.entity.PriceEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class PriceEntityBuilder extends BaseBuilder<PriceEntity> {

    public PriceEntityBuilder() {
        super(PriceEntity.class);
    }

    public PriceEntityBuilder withValidFields() {
        return this.withId(UUID.randomUUID().toString())
                .withLowerBound(1)
                .withUpperBound(100)
                .withCost(new BigDecimal("10.00"));
    }

    public PriceEntityBuilder withId(final String id) {
        data.setId(id);
        return this;
    }

    public PriceEntityBuilder withLowerBound(final Integer lowerBound) {
        data.setLowerBound(lowerBound);
        return this;
    }

    public PriceEntityBuilder withUpperBound(final Integer upperBound) {
        data.setUpperBound(upperBound);
        return this;
    }

    public PriceEntityBuilder withCost(final BigDecimal cost) {
        data.setCost(cost);
        return this;
    }

}
