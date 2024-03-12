package com.project.parkinglot.service.park;

import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.dto.response.ParkCheckInResponse;
import com.project.parkinglot.model.entity.ParkingAreaEntity;

public interface ParkService {

    ParkCheckInResponse checkIn(final String userId, final ParkCheckInRequest requestBody);

    Integer countCurrentParks(ParkingAreaEntity parkingAreaEntity);

}
