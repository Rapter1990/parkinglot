package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.dto.request.priceList.PriceListCreateRequest;


public class ParkingAreaCreateRequestBuilder extends BaseBuilder<ParkingAreaCreateRequest> {

    public ParkingAreaCreateRequestBuilder() {
        super(ParkingAreaCreateRequest.class);
    }

    public ParkingAreaCreateRequestBuilder withValidFields() {
        Faker faker = new Faker();
        return this
                .withName(faker.name().name())
                .withLocation(faker.address().fullAddress())
                .withCapacity(faker.number().numberBetween(1, 100))
                .withCity(faker.address().city())
                .withPriceList(new PriceListCreateRequest());
    }

    public ParkingAreaCreateRequestBuilder withName(
            final String name
    ) {
        data.setName(name);
        return this;
    }

    public ParkingAreaCreateRequestBuilder withLocation(
            final String location
    ) {
        data.setLocation(location);
        return this;
    }

    public ParkingAreaCreateRequestBuilder withCapacity(
            final Integer capacity
    ) {
        data.setCapacity(capacity);
        return this;
    }

    public ParkingAreaCreateRequestBuilder withCity(
            final String city
    ) {
        data.setCity(city);
        return this;
    }

    public ParkingAreaCreateRequestBuilder withPriceList(
            final PriceListCreateRequest priceList
    ) {
        data.setPriceListRequest(priceList);
        return this;
    }

    @Override
    public ParkingAreaCreateRequest build() {
        return super.build();
    }

}

