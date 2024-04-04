package com.project.parkinglot.service.park;

import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.dto.request.park.ParkCheckOutRequest;
import com.project.parkinglot.model.dto.response.park.ParkCheckInResponse;
import com.project.parkinglot.model.dto.response.park.ParkCheckOutResponse;
import com.project.parkinglot.model.entity.ParkingAreaEntity;

public interface ParkService {

    ParkCheckInResponse checkIn(final String userId, final ParkCheckInRequest requestBody);

    Integer countCurrentParks(ParkingAreaEntity parkingAreaEntity);

    ParkCheckOutResponse checkOut(
            final String userId,
            final ParkCheckOutRequest parkCheckOutRequest
    );

}
