package com.project.parkinglot.controller;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.service.parking_area.ParkingAreaCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/parking-area")
@RequiredArgsConstructor
@Validated
public class ParkingAreaController {

    private final ParkingAreaCreateService parkingAreaCreateService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> createParkingArea(
            @RequestBody final ParkingAreaCreateRequest parkingAreaCreateRequest
    ) {
        final ParkingArea parkingArea = parkingAreaCreateService
                .createParkingArea(parkingAreaCreateRequest);

        return ResponseEntity.ok(parkingArea.getId());
    }

}
