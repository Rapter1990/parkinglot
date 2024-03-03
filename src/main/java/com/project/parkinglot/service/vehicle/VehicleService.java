package com.project.parkinglot.service.vehicle;

import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.Vehicle.VehicleRequest;

public interface VehicleService {

    Vehicle assignVehicleToUser (
            String id,
            VehicleRequest vehicleRequest
    );

}
