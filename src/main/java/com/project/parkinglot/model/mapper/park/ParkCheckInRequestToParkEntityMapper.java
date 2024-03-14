package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkCheckInRequestToParkEntityMapper extends BaseMapper<ParkCheckInRequest, ParkEntity> {

    @Override
    @Mapping(source = "parkingAreaId", target = "parkingAreaEntity.id")
    @Mapping(source = "vehicle", target = "vehicleEntity")
    ParkEntity map(ParkCheckInRequest source);

    static ParkCheckInRequestToParkEntityMapper initialize() {
        return Mappers.getMapper(ParkCheckInRequestToParkEntityMapper.class);
    }

}
