package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.dto.response.park.ParkCheckOutResponse;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ParkEntityToParkCheckOutResponseMapper} to convert {@link ParkEntity} objects to {@link ParkCheckOutResponse} objects.
 */
@Mapper
public interface ParkEntityToParkCheckOutResponseMapper extends BaseMapper<ParkEntity, ParkCheckOutResponse> {


    /**
     * Maps a {@link ParkEntity} object to a {@link ParkCheckOutResponse} object.
     *
     * @param source The {@link ParkEntity} object to map.
     * @return The mapped {@link ParkCheckOutResponse} object.
     */
    @Override
    @Mapping(source = "parkingAreaEntity.id", target = "parkingAreaId")
    @Mapping(source = "parkingAreaEntity.name", target = "parkingAreaName")
    @Mapping(source = "vehicleEntity", target = "vehicleCheckOutResponse")
    @Mapping(source = "parkStatus", target = "parkStatus")
    @Mapping(source = "checkOut", target = "checkOut")
    @Mapping(source = "totalCost", target = "totalCost")
    ParkCheckOutResponse map(ParkEntity source);

    /**
     * Initializes the mapper.
     *
     * @return An instance of {@link ParkEntityToParkCheckOutResponseMapper}.
     */
    static ParkEntityToParkCheckOutResponseMapper initialize() {
        return Mappers.getMapper(ParkEntityToParkCheckOutResponseMapper.class);
    }

}
