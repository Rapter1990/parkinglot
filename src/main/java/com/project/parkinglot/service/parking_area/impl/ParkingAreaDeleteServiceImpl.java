package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.parking_area.ParkingAreaDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ParkingAreaDeleteServiceImpl implements ParkingAreaDeleteService {


    private final ParkingAreaRepository parkingAreaRepository;

    @Override
    public void deleteParkingAreaById(
            final String parkingAreaId
    ) {

        final ParkingAreaEntity parkingAreaEntityToBeDelete = parkingAreaRepository.findById(parkingAreaId)
                .orElseThrow(() -> new ParkingAreaNotFoundException("With given parkingAreaId: " + parkingAreaId));

        parkingAreaRepository.delete(parkingAreaEntityToBeDelete);

    }

}
