package com.project.parkinglot.model.mapper.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ParkingAreaDomainModelToParkingAreaEntityMapper extends BaseMapper<ParkingArea, ParkingAreaEntity> {

    @Override
    ParkingAreaEntity map(ParkingArea source);

    static ParkingAreaDomainModelToParkingAreaEntityMapper initialize(){
        return Mappers.getMapper(ParkingAreaDomainModelToParkingAreaEntityMapper.class);
    }
}
