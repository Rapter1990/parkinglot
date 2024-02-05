package com.project.parkinglot.model.mapper.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingAreaEntityToParkingAreaDomainModelMapper extends BaseMapper<ParkingAreaEntity, ParkingArea> {

    @Override
    @Mapping(source = "priceListEntity", target = "priceList")
    ParkingArea map(ParkingAreaEntity source);

    static ParkingAreaEntityToParkingAreaDomainModelMapper initialize() {
        return Mappers.getMapper(ParkingAreaEntityToParkingAreaDomainModelMapper.class);
    }

}