package com.project.parkinglot.service.parking_area;

import com.project.parkinglot.model.ParkingArea;

public interface ParkingAreaService {
    ParkingArea getParkingAreaById(
            final String parkingAreaId
    );
}
