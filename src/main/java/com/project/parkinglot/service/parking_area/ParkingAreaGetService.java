package com.project.parkinglot.service.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.response.parkingarea.ParkingAreaIncomeResponse;

import java.time.LocalDate;

public interface ParkingAreaGetService {

    ParkingArea getParkingAreaById(final String parkingAreaId);

    ParkingArea getParkingAreaByName(final String name);

    ParkingAreaIncomeResponse getDailyIncome(final LocalDate date, final String parkingAreaId);

}
