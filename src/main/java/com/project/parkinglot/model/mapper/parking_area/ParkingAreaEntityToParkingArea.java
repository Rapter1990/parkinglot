package com.project.parkinglot.model.mapper.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingAreaEntityToParkingArea extends BaseMapper<ParkingAreaEntity,ParkingArea> {

    @Override
    ParkingArea map(ParkingAreaEntity source);

    static ParkingAreaEntityToParkingArea initialize(){
        return Mappers.getMapper(ParkingAreaEntityToParkingArea.class);
    }

}
