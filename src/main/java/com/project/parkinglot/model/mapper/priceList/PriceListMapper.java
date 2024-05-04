package com.project.parkinglot.model.mapper.priceList;

import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.entity.PriceListEntity;
import com.project.parkinglot.model.mapper.price.PriceMapper;

/**
 * Mapper class named {@link PriceListMapper} to map between {@link PriceList} and {@link PriceListEntity}.
 */
public class PriceListMapper {

    /**
     * Maps a {@link PriceListEntity} object to a {@link PriceList} domain model.
     *
     * @param priceListEntity The {@link PriceListEntity} object to map.
     * @return The mapped {@link PriceList} domain model object.
     */
    public static PriceList toDomainModel(
            final PriceListEntity priceListEntity
    ) {
        return PriceList.builder()
                .id(priceListEntity.getId())
                .name(priceListEntity.getName())
                .prices(
                        priceListEntity.getPriceEntities() == null ? null :
                                priceListEntity.getPriceEntities().stream()
                                        .map(PriceMapper::toDomainModel)
                                        .toList()
                )
                .createdAt(priceListEntity.getCreatedAt())
                .createdUser(priceListEntity.getCreatedUser())
                .updatedAt(priceListEntity.getUpdatedAt())
                .updatedUser(priceListEntity.getUpdatedUser())
                .build();
    }

    /**
     * Maps a {@link PriceList} domain model object to a {@link PriceListEntity} object.
     *
     * @param priceList The {@link PriceList} domain model object to map.
     * @return The mapped {@link PriceListEntity} object.
     */
    public static PriceListEntity toEntity(
            final PriceList priceList
    ) {
        return PriceListEntity.builder()
                .id(priceList.getId())
                .name(priceList.getName())
                .createdAt(priceList.getCreatedAt())
                .createdUser(priceList.getCreatedUser())
                .updatedAt(priceList.getUpdatedAt())
                .updatedUser(priceList.getUpdatedUser())
                .build();
    }

}
