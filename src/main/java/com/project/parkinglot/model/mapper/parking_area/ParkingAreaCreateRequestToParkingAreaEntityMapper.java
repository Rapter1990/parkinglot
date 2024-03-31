package com.project.parkinglot.model.mapper.parking_area;

import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingAreaCreateRequestToParkingAreaEntityMapper extends BaseMapper<ParkingAreaCreateRequest, ParkingAreaEntity> {

    @Override
    @Mapping(target = "priceListEntity", ignore = true)
    ParkingAreaEntity map(ParkingAreaCreateRequest source);

    /**
     * Initializes the mapper.
     *
     * @return the initialized mapper object.
     */
    static ParkingAreaCreateRequestToParkingAreaEntityMapper initialize() {
        return Mappers.getMapper(ParkingAreaCreateRequestToParkingAreaEntityMapper.class);
    }

}
