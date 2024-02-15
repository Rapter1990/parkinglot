package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parkingArea.ParkingAreaUpdateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaDomainModelToParkingAreaEntityMapper;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaDomainModel;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.parking_area.ParkingAreaUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingAreaUpdateServiceImpl implements ParkingAreaUpdateService {
    private final ParkingAreaRepository parkingAreaRepository;

    private final ParkingAreaEntityToParkingAreaDomainModel parkingAreaEntityToParkingAreaDomainModel =
            ParkingAreaEntityToParkingAreaDomainModel.initialize();

    @Override
    public ParkingArea parkingAreaUpdateByCapacity(
            String parkingAreaId,
            ParkingAreaUpdateRequest parkingAreaUpdateRequest
    ) {
        final ParkingAreaEntity parkingAreaFromDB = parkingAreaRepository
                .findById(parkingAreaId)
                .orElseThrow( ()->new ParkingAreaNotFoundException("ParkingArea not found given id" + parkingAreaId));

        parkingAreaFromDB.setCapacity(parkingAreaUpdateRequest.getCapacity());

        parkingAreaRepository.save(parkingAreaFromDB);

        return parkingAreaEntityToParkingAreaDomainModel.map(parkingAreaFromDB);
    }
}
