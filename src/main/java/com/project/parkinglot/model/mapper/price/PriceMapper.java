package com.project.parkinglot.model.mapper.price;

import com.project.parkinglot.model.Price;
import com.project.parkinglot.model.entity.PriceEntity;

/**
 * Mapper class named {@link PriceMapper} to map {@link PriceEntity} to {@link Price}.
 */
public class PriceMapper {

    /**
     * Maps a {@link PriceEntity} object to a {@link Price} domain model object.
     *
     * @param priceEntity The {@link PriceEntity} object to map.
     * @return The mapped {@link Price} domain model object.
     */
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
