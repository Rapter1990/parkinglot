package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.Park;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkEntityToParkMapper extends BaseMapper<ParkEntity, Park> {

    @Override
    @Mapping(source = "parkingAreaEntity", target = "parkingArea")
    @Mapping(source = "vehicleEntity", target = "vehicle")
    Park map(ParkEntity source);


    static ParkEntityToParkMapper initialize() {
        return Mappers.getMapper(ParkEntityToParkMapper.class);
    }

}
