package com.project.parkinglot.controller;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.service.parking_area.ParkingAreaCreateService;
import com.project.parkinglot.service.parking_area.ParkingAreaDeleteService;
import com.project.parkinglot.service.parking_area.ParkingAreaGetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/parking-area")
@RequiredArgsConstructor
@Validated
public class ParkingAreaController {

    private final ParkingAreaCreateService parkingAreaCreateService;
    private final ParkingAreaDeleteService parkingAreaDeleteService;
    private final ParkingAreaGetService parkingAreaGetService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomResponse<String> createParkingArea(@RequestBody @Valid final ParkingAreaCreateRequest parkingAreaCreateRequest) {

        final ParkingArea parkingArea = parkingAreaCreateService
                .createParkingArea(parkingAreaCreateRequest);

        return CustomResponse.ok(parkingArea.getId());

    }

    @GetMapping("/{parkingAreaId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomResponse<ParkingArea> getParkingAreaById(@PathVariable("parkingAreaId") @UUID final String parkingAreaId) {

        final ParkingArea parkingArea = parkingAreaGetService.getParkingAreaById(parkingAreaId);

        return CustomResponse.ok(parkingArea);

    }

    @DeleteMapping("/{parkingAreaId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomResponse<String> deleteParkingAreaById(@PathVariable("parkingAreaId") @UUID final String parkingAreaId) {

        parkingAreaDeleteService.deleteParkingAreaById(parkingAreaId);

        return CustomResponse.ok("Parking area with id " + parkingAreaId + " is deleted");

    }

}
