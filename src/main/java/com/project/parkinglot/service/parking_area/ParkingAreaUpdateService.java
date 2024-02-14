package com.project.parkinglot.service.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parkingArea.ParkingAreaUpdateRequest;

public interface ParkingAreaUpdateService {
     ParkingArea parkingAreaUpdateByCapacity(
             final String parkingAreaId,
             final ParkingAreaUpdateRequest parkingAreaUpdateRequest
     );
}
