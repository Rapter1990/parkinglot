package com.project.parkinglot.model.mapper.vehicle;

import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleEntityToVehicleMapper extends BaseMapper<VehicleEntity, Vehicle> {

    @Override
    @Mapping(source = "parkEntities",target = "parkList")
    Vehicle map(VehicleEntity source);

    static VehicleEntityToVehicleMapper initialize(){
        return Mappers.getMapper(VehicleEntityToVehicleMapper.class);
    }
}
