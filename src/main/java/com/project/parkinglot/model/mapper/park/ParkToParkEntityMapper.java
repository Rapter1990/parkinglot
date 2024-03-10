package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.Park;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkToParkEntityMapper extends BaseMapper<Park, ParkEntity> {

    @Override
    @Mapping(source = "parkingArea", target = "parkingAreaEntity")
    @Mapping(source = "vehicle", target = "vehicleEntity")
    ParkEntity map(Park source);

    static ParkToParkEntityMapper initialize() {
        return Mappers.getMapper(ParkToParkEntityMapper.class);
    }

}

