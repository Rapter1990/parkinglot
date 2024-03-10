package com.project.parkinglot.builder;

import com.project.parkinglot.model.Price;

import java.math.BigDecimal;
import java.util.UUID;

public class PriceBuilder extends BaseBuilder<Price> {

    public PriceBuilder() {
        super(Price.class);
    }

    public PriceBuilder withValidFields() {
        return this
                .withId(UUID.randomUUID().toString())
                .withLowerBound(1)
                .withUpperBound(100)
                .withCost(new BigDecimal("10.00"));
    }

    public PriceBuilder withId(final String id) {
        data.setId(id);
        return this;
    }

    public PriceBuilder withLowerBound(final Integer lowerBound) {
        data.setLowerBound(lowerBound);
        return this;
    }

    public PriceBuilder withUpperBound(final Integer upperBound) {
        data.setUpperBound(upperBound);
        return this;
    }

    public PriceBuilder withCost(final BigDecimal cost) {
        data.setCost(cost);
        return this;
    }

}
