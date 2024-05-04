package com.project.parkinglot.service.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.response.parkingarea.ParkingAreaIncomeResponse;

import java.time.LocalDate;

/**
 * Service interface named {@link ParkingAreaGetService} for retrieving parking areas.
 */
public interface ParkingAreaGetService {

    /**
     * Retrieves a parking area by its ID.
     *
     * @param parkingAreaId the ID of the parking area to retrieve
     * @return the retrieved parking area
     */
    ParkingArea getParkingAreaById(final String parkingAreaId);

    /**
     * Retrieves a parking area by its name.
     *
     * @param name the name of the parking area to retrieve
     * @return the retrieved parking area
     */
    ParkingArea getParkingAreaByName(final String name);

    /**
     * Retrieves daily income for a parking area on a given date.
     *
     * @param date         the date for which to retrieve income
     * @param parkingAreaId the ID of the parking area
     * @return the daily income of the parking area
     */
    ParkingAreaIncomeResponse getDailyIncome(final LocalDate date, final String parkingAreaId);

}
