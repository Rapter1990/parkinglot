package com.project.parkinglot.model.mapper.vehicle;

import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.Vehicle.VehicleRequest;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleRequestToVehicleMapper extends BaseMapper<VehicleRequest, Vehicle> {

    @Override
    Vehicle map(VehicleRequest source);

    static VehicleRequestToVehicleMapper initialize(){
        return Mappers.getMapper(VehicleRequestToVehicleMapper.class);
    }
}
