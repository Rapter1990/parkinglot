package com.project.parkinglot.model.mapper.priceList;

import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.entity.PriceListEntity;
import com.project.parkinglot.model.mapper.price.PriceMapper;

public class PriceListMapper {

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
