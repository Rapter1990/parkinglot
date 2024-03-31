package com.project.parkinglot.service.vehicle;

import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.vehicle.VehicleRequest;
import com.project.parkinglot.model.dto.response.VehicleParkingDetailResponse;

public interface VehicleService {

    Vehicle assignVehicleToUser (
            String id,
            VehicleRequest vehicleRequest
    );

    Vehicle assignOrGet(final String userId, final VehicleRequest vehicleRequest);

    VehicleParkingDetailResponse getParkingDetails(final String licensePlate);

}
