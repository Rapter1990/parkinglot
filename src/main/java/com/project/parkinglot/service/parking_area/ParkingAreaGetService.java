package com.project.parkinglot.service.parking_area;

import com.project.parkinglot.model.ParkingArea;

public interface ParkingAreaGetService {

    ParkingArea getParkingAreaById(final String parkingAreaId);

    ParkingArea getParkingAreaByName(final String name);

}
