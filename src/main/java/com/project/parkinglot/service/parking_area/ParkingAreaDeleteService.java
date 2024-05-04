package com.project.parkinglot.service.parking_area;

/**
 * Service interface named {@link ParkingAreaDeleteService} for deleting parking areas.
 */
public interface ParkingAreaDeleteService {

    /**
     * Deletes a parking area by its ID.
     *
     * @param parkingAreaId the ID of the parking area to delete
     */
    void deleteParkingAreaById(
            final String parkingAreaId
    );

}
