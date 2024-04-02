package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.dto.response.ParkDetailResponse;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkEntityToParkDetailResponse extends BaseMapper<ParkEntity, ParkDetailResponse> {

    @Override
    @Mapping(source = "checkIn", target = "checkInDate")
    @Mapping(source = "checkOut", target = "checkOutDate")
    @Mapping(source = "totalCost", target = "totalCost")
    @Mapping(source = "parkingAreaEntity.name", target = "parkingAreaName")
    ParkDetailResponse map(ParkEntity source);


    static ParkEntityToParkDetailResponse initialize() {
        return Mappers.getMapper(ParkEntityToParkDetailResponse.class);
    }

}
