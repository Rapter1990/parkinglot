package com.project.parkinglot.model.mapper.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingAreaEntityToParkingAreaMapper extends BaseMapper<ParkingAreaEntity, ParkingArea> {

    @Override
    @Mapping(source = "priceListEntity", target = "priceList")
    ParkingArea map(ParkingAreaEntity source);

    static ParkingAreaEntityToParkingAreaMapper initialize() {
        return Mappers.getMapper(ParkingAreaEntityToParkingAreaMapper.class);
    }

}
