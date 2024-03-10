package com.project.parkinglot.model.mapper.park;

import com.project.parkinglot.model.Park;
import com.project.parkinglot.model.dto.response.ParkCheckInResponse;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkToParkCheckInResponseMapper extends BaseMapper<Park, ParkCheckInResponse> {

    @Override
    @Mapping(source = "id", target = "parkingAreaId")
    @Mapping(source = "vehicle", target = "vehicleCheckInResponse")
    @Mapping(source = "parkStatus", target = "parkStatus")
    @Mapping(source = "checkIn", target = "checkIn")
    ParkCheckInResponse map(Park source);

    static ParkToParkCheckInResponseMapper initialize() {
        return Mappers.getMapper(ParkToParkCheckInResponseMapper.class);
    }

}
