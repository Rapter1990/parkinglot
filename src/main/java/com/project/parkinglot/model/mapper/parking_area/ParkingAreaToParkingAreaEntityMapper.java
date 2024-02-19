package com.project.parkinglot.model.mapper.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingAreaToParkingAreaEntityMapper extends BaseMapper<ParkingArea, ParkingAreaEntity> {

    @Override
    ParkingAreaEntity map(ParkingArea source);

    static ParkingAreaToParkingAreaEntityMapper initialize(){
        return Mappers.getMapper(ParkingAreaToParkingAreaEntityMapper.class);
    }

}
