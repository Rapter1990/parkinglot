package com.project.parkinglot.controller;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.service.parking_area.ParkingAreaService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parking-area")
@RequiredArgsConstructor
@Validated
public class ParkingAreaController {

    private final ParkingAreaService parkingAreaService;

    @GetMapping("/{parking-area-id}")
    public ResponseEntity<ParkingArea> getParkingAreaById(
        @PathVariable("parking-area-id") @UUID final String parkingAreaId
    ) {
        final ParkingArea parkingArea = parkingAreaService
                .getParkingAreaById(parkingAreaId);

        return ResponseEntity.ok(parkingArea);
    }

}
