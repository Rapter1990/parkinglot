package com.project.parkinglot.model.mapper.vehicle;

import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleToVehicleEntityMapper extends BaseMapper<Vehicle,VehicleEntity> {

    @Override
    VehicleEntity map(Vehicle source);

    static VehicleToVehicleEntityMapper initialize(){
        return Mappers.getMapper(VehicleToVehicleEntityMapper.class);
    }

}
