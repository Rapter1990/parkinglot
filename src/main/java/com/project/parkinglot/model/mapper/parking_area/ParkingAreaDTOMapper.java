package com.project.parkinglot.model.mapper.parking_area;

import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.priceList.PriceListDTOMapper;

public class ParkingAreaDTOMapper {

    public static ParkingAreaEntity mapForSaving(
            final ParkingAreaCreateRequest parkingAreaCreateRequest
    ) {
        return ParkingAreaEntity.builder()
                .name(parkingAreaCreateRequest.getName())
                .location(parkingAreaCreateRequest.getLocation())
                .capacity(parkingAreaCreateRequest.getCapacity())
                .city(parkingAreaCreateRequest.getCity())
                .priceListEntity(PriceListDTOMapper
                        .mapForSaving(
                                parkingAreaCreateRequest
                                        .getPriceListRequest()
                        )
                )
                .build();
    }

}
