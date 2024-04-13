package com.project.parkinglot.controller;

import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.dto.request.park.ParkCheckOutRequest;
import com.project.parkinglot.model.dto.response.park.ParkCheckInResponse;
import com.project.parkinglot.model.dto.response.park.ParkCheckOutResponse;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.service.park.ParkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parks")
@RequiredArgsConstructor
@Validated
public class ParkController {

    private final ParkService parkService;

    @PostMapping("/userId/{userId}/check-in")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public CustomResponse<ParkCheckInResponse> checkIn(@PathVariable @UUID final String userId, @RequestBody @Valid ParkCheckInRequest parkCheckInRequest) {
        final ParkCheckInResponse parkCheckInResponse = parkService.checkIn(userId, parkCheckInRequest);
        return CustomResponse.ok(parkCheckInResponse);
    }

    @PostMapping("/userId/{userId}/check-out")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public CustomResponse<ParkCheckOutResponse> checkOut(
            @PathVariable("userId") @UUID final String userId,
            @RequestBody @Valid ParkCheckOutRequest parkCheckOutRequest
    ) {
        final ParkCheckOutResponse parkCheckOutResponse = parkService
                .checkOut(userId, parkCheckOutRequest);

        return CustomResponse.ok(parkCheckOutResponse);
    }

}
