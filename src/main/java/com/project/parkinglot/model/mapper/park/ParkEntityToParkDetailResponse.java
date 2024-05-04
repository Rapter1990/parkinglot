package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.dto.response.ParkDetailResponse;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ParkEntityToParkDetailResponse} to convert {@link ParkEntity} objects to {@link ParkDetailResponse} objects.
 */
@Mapper
public interface ParkEntityToParkDetailResponse extends BaseMapper<ParkEntity, ParkDetailResponse> {

    /**
     * Maps a {@link ParkEntity} object to a {@link ParkDetailResponse} object.
     *
     * @param source The {@link ParkEntity} object to map.
     * @return The mapped {@link ParkDetailResponse} object.
     */
    @Override
    @Mapping(source = "checkIn", target = "checkInDate")
    @Mapping(source = "checkOut", target = "checkOutDate")
    @Mapping(source = "totalCost", target = "totalCost")
    @Mapping(source = "parkingAreaEntity.name", target = "parkingAreaName")
    ParkDetailResponse map(ParkEntity source);

    /**
     * Initializes the mapper.
     *
     * @return An instance of {@link ParkEntityToParkDetailResponse}.
     */
    static ParkEntityToParkDetailResponse initialize() {
        return Mappers.getMapper(ParkEntityToParkDetailResponse.class);
    }

}
