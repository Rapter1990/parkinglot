package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.dto.response.park.ParkCheckOutResponse;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkEntityToParkCheckOutResponseMapper extends BaseMapper<ParkEntity, ParkCheckOutResponse> {


    @Override
    @Mapping(source = "parkingAreaEntity.id", target = "parkingAreaId")
    @Mapping(source = "parkingAreaEntity.name", target = "parkingAreaName")
    @Mapping(source = "vehicleEntity", target = "vehicleCheckOutResponse")
    @Mapping(source = "parkStatus", target = "parkStatus")
    @Mapping(source = "checkOut", target = "checkOut")
    @Mapping(source = "totalCost", target = "totalCost")
    ParkCheckOutResponse map(ParkEntity source);

    static ParkEntityToParkCheckOutResponseMapper initialize() {
        return Mappers.getMapper(ParkEntityToParkCheckOutResponseMapper.class);
    }

}
