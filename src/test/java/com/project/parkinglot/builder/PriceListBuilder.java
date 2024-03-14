package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.Price;
import com.project.parkinglot.model.PriceList;

import java.util.List;
import java.util.UUID;

public class PriceListBuilder extends BaseBuilder<PriceList> {

    public PriceListBuilder() {
        super(PriceList.class);
    }

    public PriceListBuilder withValidFields() {
        final Faker faker = new Faker();
        return this
                .withId(UUID.randomUUID().toString())
                .withName(faker.name().name())
                .withPrices(List.of(new PriceBuilder().withValidFields().build()));
    }

    public PriceListBuilder withId(final String id) {
        data.setId(id);
        return this;
    }

    public PriceListBuilder withName(final String name) {
        data.setName(name);
        return this;
    }

    public PriceListBuilder withPrices(final List<Price> prices) {
        data.setPrices(prices);
        return this;
    }
}
