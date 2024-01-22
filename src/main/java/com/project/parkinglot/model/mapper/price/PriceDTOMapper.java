package com.project.parkinglot.model.mapper.price;

import com.project.parkinglot.model.dto.request.price.PriceCreateRequest;
import com.project.parkinglot.model.entity.PriceEntity;

public class PriceDTOMapper {

    public static PriceEntity mapForSaving(
            final PriceCreateRequest priceCreateRequest
    ) {
        return PriceEntity.builder()
                .lowerBound(priceCreateRequest.getLowerBound())
                .upperBound(priceCreateRequest.getUpperBound())
                .cost(priceCreateRequest.getCost())
                .build();
    }

}
