package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.dto.request.price.PriceCreateRequest;
import com.project.parkinglot.model.dto.request.priceList.PriceListCreateRequest;

import java.util.List;


public class PriceListCreateRequestBuilder extends BaseBuilder<PriceListCreateRequest> {

    public PriceListCreateRequestBuilder() {
        super(PriceListCreateRequest.class);
    }

    public PriceListCreateRequestBuilder withValidFields() {
        Faker faker = new Faker();
        return this
                .withName(faker.name().name())
                .withPriceCreateRequestList(List.of(
                        new PriceCreateRequestBuilder()
                                .withValidFields()
                                .build())
                );

    }

    public PriceListCreateRequestBuilder withName(
            final String name
    ) {
        data.setName(name);
        return this;
    }

    public PriceListCreateRequestBuilder withPriceCreateRequest(
            final PriceCreateRequest priceCreateRequest
    ) {
        data.setPrices(List.of(priceCreateRequest));
        return this;
    }

    public PriceListCreateRequestBuilder withPriceCreateRequestList(
            final List<PriceCreateRequest> priceCreateRequestList
    ) {
        data.setPrices(priceCreateRequestList);
        return this;
    }

    @Override
    public PriceListCreateRequest build() {
        return super.build();
    }

}
