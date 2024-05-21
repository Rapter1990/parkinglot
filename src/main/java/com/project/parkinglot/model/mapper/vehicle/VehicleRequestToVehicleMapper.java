package com.project.parkinglot.model.mapper.vehicle;

import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.vehicle.VehicleRequest;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link VehicleRequestToVehicleMapper} to map from {@link VehicleRequest} to {@link Vehicle}.
 */
@Mapper
public interface VehicleRequestToVehicleMapper extends BaseMapper<VehicleRequest, Vehicle> {

    /**
     * Maps a {@link VehicleRequest} object to a {@link Vehicle} domain model object.
     *
     * @param source The {@link VehicleRequest} object to map.
     * @return The mapped {@link Vehicle} domain model object.
     */
    @Override
    Vehicle map(VehicleRequest source);

    /**
     * Initializes the mapper.
     *
     * @return The initialized mapper object.
     */
    static VehicleRequestToVehicleMapper initialize() {
        return Mappers.getMapper(VehicleRequestToVehicleMapper.class);
    }

}
