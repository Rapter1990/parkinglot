package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.Park;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ParkEntityToParkMapper} to convert {@link ParkEntity} objects to {@link Park} objects.
 */
@Mapper
public interface ParkEntityToParkMapper extends BaseMapper<ParkEntity, Park> {

    /**
     * Maps a {@link ParkEntity} object to a {@link Park} object.
     *
     * @param source The {@link ParkEntity} object to map.
     * @return The mapped {@link Park} object.
     */
    @Override
    @Mapping(source = "parkingAreaEntity", target = "parkingArea")
    @Mapping(source = "vehicleEntity", target = "vehicle")
    Park map(ParkEntity source);

    /**
     * Initializes the mapper.
     *
     * @return An instance of {@link ParkEntityToParkMapper}.
     */
    static ParkEntityToParkMapper initialize() {
        return Mappers.getMapper(ParkEntityToParkMapper.class);
    }

}
