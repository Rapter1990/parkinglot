package com.project.parkinglot.model.mapper.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.priceList.PriceListMapper;

public class ParkingAreaMapper {

    public static ParkingArea toDomainModel(
            final ParkingAreaEntity parkingAreaEntity
    ) {
        return ParkingArea.builder()
                .id(parkingAreaEntity.getId())
                .name(parkingAreaEntity.getName())
                .location(parkingAreaEntity.getLocation())
                .capacity(parkingAreaEntity.getCapacity())
                .city(parkingAreaEntity.getCity())
                .priceList(PriceListMapper
                        .toDomainModel(
                                parkingAreaEntity
                                        .getPriceListEntity()
                        )
                )
                .createdAt(parkingAreaEntity.getCreatedAt())
                .createdUser(parkingAreaEntity.getCreatedUser())
                .updatedAt(parkingAreaEntity.getUpdatedAt())
                .updatedUser(parkingAreaEntity.getUpdatedUser())
                .build();
    }

}
