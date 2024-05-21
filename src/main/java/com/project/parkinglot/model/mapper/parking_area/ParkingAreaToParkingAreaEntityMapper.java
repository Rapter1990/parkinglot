package com.project.parkinglot.model.mapper.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ParkingAreaToParkingAreaEntityMapper} to convert {@link ParkingArea} objects to {@link ParkingAreaEntity} objects.
 */
@Mapper
public interface ParkingAreaToParkingAreaEntityMapper extends BaseMapper<ParkingArea, ParkingAreaEntity> {

    /**
     * Maps a {@link ParkingArea} object to a {@link ParkingAreaEntity} object.
     *
     * @param source The {@link ParkingArea} object to map.
     * @return The mapped {@link ParkingAreaEntity} object.
     */
    @Override
    ParkingAreaEntity map(ParkingArea source);

    /**
     * Initializes the mapper.
     *
     * @return The initialized mapper object.
     */
    static ParkingAreaToParkingAreaEntityMapper initialize() {
        return Mappers.getMapper(ParkingAreaToParkingAreaEntityMapper.class);
    }

}
