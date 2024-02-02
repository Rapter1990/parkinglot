package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.exception.parkingarea.ParkingAreAlreadyExistException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaDTOMapper;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaMapper;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.parking_area.ParkingAreaCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingAreaCreateServiceImpl implements ParkingAreaCreateService {

    private final ParkingAreaRepository parkingAreaRepository;

    @Override
    public ParkingArea createParkingArea(
            final ParkingAreaCreateRequest parkingAreaCreateRequest
    ) {
        this.checkParkingAreaNameAndLocationUniqueness(
                parkingAreaCreateRequest.getName(),
                parkingAreaCreateRequest.getLocation()
        );

        ParkingAreaEntity parkingAreaToBeCreate = ParkingAreaDTOMapper
                .mapForSaving(parkingAreaCreateRequest);

        parkingAreaRepository.save(parkingAreaToBeCreate);

        return ParkingAreaMapper
                .toDomainModel(parkingAreaToBeCreate);
    }

    private void checkParkingAreaNameAndLocationUniqueness(
            final String name,
            final String location
    ) {
        if (Boolean.TRUE.equals(parkingAreaRepository
                .existsParkingAreaEntitiesByNameAndLocation(name, location))) {
            throw new ParkingAreAlreadyExistException();
        }
    }
}

