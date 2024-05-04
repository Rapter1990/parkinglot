package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.Park;
import com.project.parkinglot.model.dto.response.park.ParkCheckInResponse;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link ParkToParkCheckInResponseMapper} to convert {@link Park} objects to {@link ParkCheckInResponse} objects.
 */
@Mapper
public interface ParkToParkCheckInResponseMapper extends BaseMapper<Park, ParkCheckInResponse> {

    /**
     * Maps a {@link Park} object to a {@link ParkCheckInResponse} object.
     *
     * @param source The {@link Park} object to map.
     * @return The mapped {@link ParkCheckInResponse} object.
     */
    @Override
    @Mapping(source = "parkingArea.id", target = "parkingAreaId")
    @Mapping(source = "vehicle", target = "vehicleCheckInResponse")
    @Mapping(source = "parkStatus", target = "parkStatus")
    @Mapping(source = "checkIn", target = "checkIn")
    ParkCheckInResponse map(Park source);

    /**
     * Initializes the mapper.
     *
     * @return An instance of {@link ParkToParkCheckInResponseMapper}.
     */
    static ParkToParkCheckInResponseMapper initialize() {
        return Mappers.getMapper(ParkToParkCheckInResponseMapper.class);
    }

}
