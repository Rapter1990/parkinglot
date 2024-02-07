package com.project.parkinglot.service.parking_area;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;

public interface ParkingAreaCreateService {

    ParkingArea createParkingArea(
            final ParkingAreaCreateRequest parkingAreaCreateRequest
    );

}
