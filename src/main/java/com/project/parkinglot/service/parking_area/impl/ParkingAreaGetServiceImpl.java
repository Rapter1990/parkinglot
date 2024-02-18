package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaDomainModelMapper;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.parking_area.ParkingAreaGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingAreaGetServiceImpl implements ParkingAreaGetService {

    private final ParkingAreaRepository parkingAreaRepository;

    private final ParkingAreaEntityToParkingAreaDomainModelMapper parkingAreaEntityToParkingAreaDomainModelMapper =
            ParkingAreaEntityToParkingAreaDomainModelMapper.initialize();

    @Override
    public ParkingArea getParkingAreaById(String parkingAreaId) {

        final ParkingAreaEntity existingParkingArea = parkingAreaRepository.findById(parkingAreaId)
                .orElseThrow(() -> new ParkingAreaNotFoundException("With given parkingAreaId: " + parkingAreaId));

        return parkingAreaEntityToParkingAreaDomainModelMapper.map(existingParkingArea);

    }

}
