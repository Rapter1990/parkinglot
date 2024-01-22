package com.project.parkinglot.model.mapper.price;

import com.project.parkinglot.model.Price;
import com.project.parkinglot.model.entity.PriceEntity;

public class PriceMapper {

    public static Price toDomainModel(
            final PriceEntity priceEntity
    ) {
        return Price.builder()
                .id(priceEntity.getId())
                .lowerBound(priceEntity.getLowerBound())
                .upperBound(priceEntity.getUpperBound())
                .cost(priceEntity.getCost())
                .createdAt(priceEntity.getCreatedAt())
                .createdUser(priceEntity.getCreatedUser())
                .updatedAt(priceEntity.getUpdatedAt())
                .updatedUser(priceEntity.getUpdatedUser())
                .build();
    }

}
