package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.exception.parkingarea.ParkingAreaCapacityCanNotBeNullException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaUpdateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaMapper;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.parking_area.ParkingAreaUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParkingAreaUpdateServiceImpl implements ParkingAreaUpdateService {

    private final ParkingAreaRepository parkingAreaRepository;

    private final ParkingAreaEntityToParkingAreaMapper parkingAreaEntityToParkingArea =
            ParkingAreaEntityToParkingAreaMapper.initialize();

    @Override
    public ParkingArea parkingAreaUpdateByCapacity(
            String parkingAreaId,
            ParkingAreaUpdateRequest parkingAreaUpdateRequest
    ) {

        this.checkParkingAreaRequestCapacity(parkingAreaUpdateRequest);
        final ParkingAreaEntity existingParkingArea = parkingAreaRepository
                .findById(parkingAreaId)
                .orElseThrow( ()-> new ParkingAreaNotFoundException("ParkingArea not found given id" + parkingAreaId));

        existingParkingArea.setCapacity(parkingAreaUpdateRequest.getCapacity());

        parkingAreaRepository.save(existingParkingArea);

        return parkingAreaEntityToParkingArea.map(existingParkingArea);
    }

    private void checkParkingAreaRequestCapacity(
          ParkingAreaUpdateRequest parkingAreaUpdateRequest
    ) {
        if (Objects.isNull(parkingAreaUpdateRequest.getCapacity())) {
            throw new ParkingAreaCapacityCanNotBeNullException();
        }
    }

}
