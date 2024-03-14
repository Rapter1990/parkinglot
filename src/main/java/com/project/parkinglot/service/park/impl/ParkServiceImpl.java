package com.project.parkinglot.service.park.impl;

import com.project.parkinglot.exception.parkingarea.ParkingAreaCapacityException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.Park;
import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.dto.response.ParkCheckInResponse;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.enums.ParkStatus;
import com.project.parkinglot.model.mapper.park.ParkCheckInRequestToParkEntityMapper;
import com.project.parkinglot.model.mapper.park.ParkEntityToParkMapper;
import com.project.parkinglot.model.mapper.park.ParkToParkCheckInResponseMapper;
import com.project.parkinglot.model.mapper.park.ParkToParkEntityMapper;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaToParkingAreaEntityMapper;
import com.project.parkinglot.model.mapper.vehicle.VehicleToVehicleEntityMapper;
import com.project.parkinglot.repository.ParkRepository;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.park.ParkService;
import com.project.parkinglot.service.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkServiceImpl implements ParkService {

    private final ParkRepository parkRepository;

    private final ParkingAreaRepository parkingAreaRepository;

    private final VehicleService vehicleService;

    private final ParkCheckInRequestToParkEntityMapper parkCheckInRequestToParkEntityMapper = ParkCheckInRequestToParkEntityMapper.initialize();

    private final ParkEntityToParkMapper parkEntityToParkMapper = ParkEntityToParkMapper.initialize();

    private final ParkToParkEntityMapper parkToParkEntityMapper = ParkToParkEntityMapper.initialize();

    private final ParkToParkCheckInResponseMapper parkToParkCheckInResponseMapper = ParkToParkCheckInResponseMapper.initialize();

    private final VehicleToVehicleEntityMapper vehicleToVehicleEntityMapper = VehicleToVehicleEntityMapper.initialize();

    private final ParkingAreaToParkingAreaEntityMapper parkingAreaToParkingAreaEntityMapper = ParkingAreaToParkingAreaEntityMapper.initialize();

    @Override
    @Transactional
    public ParkCheckInResponse checkIn(final String userId, final ParkCheckInRequest parkCheckInRequest) {

        final ParkingAreaEntity existingParkingEntityArea = parkingAreaRepository.findById(parkCheckInRequest.getParkingAreaId())
                .orElseThrow(() -> new ParkingAreaNotFoundException("With given parkingAreaId: " + parkCheckInRequest.getParkingAreaId()));

        Vehicle vehicle = vehicleService.assignOrGet(userId, parkCheckInRequest.getVehicle());
        validateAvailableCapacity(existingParkingEntityArea);
        Park park = parkAvailableArea(parkCheckInRequest, vehicle, existingParkingEntityArea);
        return parkToParkCheckInResponseMapper.map(park);
    }

    @Override
    public Integer countCurrentParks(ParkingAreaEntity parkingAreaEntity) {
        return parkRepository.countByParkingAreaEntityAndParkStatus(parkingAreaEntity, ParkStatus.EMPTY);
    }

    private Park parkAvailableArea(ParkCheckInRequest checkInRequest, Vehicle vehicle, ParkingAreaEntity existingParkingEntityArea) {
        ParkEntity parkEntity = parkCheckInRequestToParkEntityMapper.map(checkInRequest);
        parkEntity.setVehicleEntity(vehicleToVehicleEntityMapper.map(vehicle));
        parkEntity.setParkingAreaEntity(existingParkingEntityArea);
        parkEntity.setParkStatus(ParkStatus.FULL);
        parkEntity.setCheckIn(LocalDateTime.now());
        ParkEntity savedParkEntity = parkRepository.save(parkEntity);
        return parkEntityToParkMapper.map(savedParkEntity);
    }

    private void validateAvailableCapacity(ParkingAreaEntity parkingAreaEntity) {
        Integer currentParks = countCurrentParks(parkingAreaEntity);
        if (parkingAreaEntity.getCapacity() <= currentParks) {
            throw new ParkingAreaCapacityException();
        }
    }

}
