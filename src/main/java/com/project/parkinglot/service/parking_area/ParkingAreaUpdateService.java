package com.project.parkinglot.service.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaUpdateRequest;

/**
 * Service interface named {@link ParkingAreaUpdateService} for updating parking areas.
 */
public interface ParkingAreaUpdateService {

    /**
     * Updates the capacity of a parking area.
     *
     * @param parkingAreaId            the ID of the parking area to update
     * @param parkingAreaUpdateRequest the request containing the new capacity
     * @return the updated parking area
     */
    ParkingArea parkingAreaUpdateByCapacity(
            final String parkingAreaId,
            final ParkingAreaUpdateRequest parkingAreaUpdateRequest
    );

}
