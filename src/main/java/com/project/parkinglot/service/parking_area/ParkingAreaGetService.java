package com.project.parkinglot.service.parking_area;

import com.project.parkinglot.model.ParkingArea;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ParkingAreaGetService {

    ParkingArea getParkingAreaById(final String parkingAreaId);

    ParkingArea getParkingAreaByName(final String name);

    BigDecimal getDailyIncome(final LocalDate date, final String parkingAreaId);
}
