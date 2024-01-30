package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaMapper;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.parking_area.ParkingAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingAreaServiceImpl implements ParkingAreaService {

    private final ParkingAreaRepository parkingAreaRepository;

    @Override
    public ParkingArea getParkingAreaById(
            final String parkingAreaId
    ) {
        final ParkingAreaEntity parkingAreaEntityFromDB = parkingAreaRepository
                .findById(parkingAreaId)
                .orElseThrow(ParkingAreaNotFoundException::new);

        return ParkingAreaMapper.toDomainModel(parkingAreaEntityFromDB);
    }
}
