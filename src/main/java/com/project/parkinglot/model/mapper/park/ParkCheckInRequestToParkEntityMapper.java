package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ParkCheckInRequestToParkEntityMapper} to convert {@link ParkCheckInRequest} objects to {@link ParkEntity} objects.
 */
@Mapper
public interface ParkCheckInRequestToParkEntityMapper extends BaseMapper<ParkCheckInRequest, ParkEntity> {

    /**
     * Maps a {@link ParkCheckInRequest} object to a {@link ParkEntity} object.
     *
     * @param source The {@link ParkCheckInRequest} object to map.
     * @return The mapped {@link ParkEntity} object.
     */
    @Override
    @Mapping(source = "parkingAreaId", target = "parkingAreaEntity.id")
    @Mapping(source = "vehicle", target = "vehicleEntity")
    ParkEntity map(ParkCheckInRequest source);

    /**
     * Initializes the mapper.
     *
     * @return An instance of {@link ParkCheckInRequestToParkEntityMapper}.
     */
    static ParkCheckInRequestToParkEntityMapper initialize() {
        return Mappers.getMapper(ParkCheckInRequestToParkEntityMapper.class);
    }

}
