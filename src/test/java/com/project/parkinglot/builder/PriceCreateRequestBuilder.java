package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.dto.request.price.PriceCreateRequest;

import java.math.BigDecimal;


public class PriceCreateRequestBuilder extends BaseBuilder<PriceCreateRequest> {

    public PriceCreateRequestBuilder() {
        super(PriceCreateRequest.class);
    }

    public PriceCreateRequestBuilder withValidFields() {
        Faker faker = new Faker();
        return this
                .withLowerBound(faker.number().randomDigit())
                .withUpperBound(faker.number().randomDigit())
                .withCost(BigDecimal.TEN);
    }

    public PriceCreateRequestBuilder withLowerBound(
            final Integer lowerBound
    ) {
        data.setLowerBound(lowerBound);
        return this;
    }

    public PriceCreateRequestBuilder withUpperBound(
            final Integer upperBound
    ) {
        data.setUpperBound(upperBound);
        return this;
    }

    public PriceCreateRequestBuilder withCost(
            final BigDecimal cost
    ) {
        data.setCost(cost);
        return this;
    }

    @Override
    public PriceCreateRequest build() {
        return super.build();
    }

}