package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.exception.parkingarea.DailyIncomeException;
import com.project.parkinglot.exception.parkingarea.InvalidDateException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.response.parkingarea.ParkingAreaIncomeResponse;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaMapper;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.parking_area.ParkingAreaGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class ParkingAreaGetServiceImpl implements ParkingAreaGetService {

    private final ParkingAreaRepository parkingAreaRepository;

    private final ParkingAreaEntityToParkingAreaMapper parkingAreaEntityToParkingAreaMapper =
            ParkingAreaEntityToParkingAreaMapper.initialize();



    @Override
    public ParkingArea getParkingAreaById(final String parkingAreaId) {

        final ParkingAreaEntity existingParkingArea = parkingAreaRepository.findById(parkingAreaId)
                .orElseThrow(() -> new ParkingAreaNotFoundException("With given parkingAreaId: " + parkingAreaId));

        return parkingAreaEntityToParkingAreaMapper.map(existingParkingArea);

    }

    @Override
    public ParkingArea getParkingAreaByName(final String name) {

        final ParkingAreaEntity existingParkingArea = parkingAreaRepository.findByName(name)
                .orElseThrow(() -> new ParkingAreaNotFoundException("With given name: " + name));

        return parkingAreaEntityToParkingAreaMapper.map(existingParkingArea);

    }

    @Override
    public ParkingAreaIncomeResponse getDailyIncome(final LocalDate date, final String parkingAreaId) {

       ParkingAreaEntity parkingAreaEntity = parkingAreaRepository
                .findById(parkingAreaId)
                .orElseThrow(ParkingAreaNotFoundException::new);

        isGivenDateAfterCurrentDate(date);

         BigDecimal calculatedIncome=parkingAreaRepository.calculateDailyIncome(date, parkingAreaId)
                .orElseThrow(DailyIncomeException::new);

         return ParkingAreaIncomeResponse.builder()
                 .income(calculatedIncome)
                 .name(parkingAreaEntity.getName())
                 .build();
    }

    private void isGivenDateAfterCurrentDate(final LocalDate date) {
        if (Boolean.TRUE.equals(date.isAfter(LocalDate.now()))) {
            throw new InvalidDateException();
        }
    }

}
