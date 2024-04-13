package com.project.parkinglot.service.vehicle;

import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.vehicle.VehicleRequest;
import com.project.parkinglot.model.dto.response.VehicleParkingDetailResponse;
import com.project.parkinglot.model.entity.VehicleEntity;

public interface VehicleService {

    Vehicle assignVehicleToUser (
            String id,
            VehicleRequest vehicleRequest
    );

    Vehicle assignOrGet(final String userId, final VehicleRequest vehicleRequest);

    VehicleEntity findByLicensePlate(final String licensePlate);

    VehicleParkingDetailResponse getParkingDetails(final String licensePlate);

}
