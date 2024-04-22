package com.project.parkinglot.controller;

import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.dto.request.park.ParkCheckOutRequest;
import com.project.parkinglot.model.dto.response.park.ParkCheckInResponse;
import com.project.parkinglot.model.dto.response.park.ParkCheckOutResponse;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.service.park.ParkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Park Management", description = "APIs related to park check-ins and check-outs for drivers")
public class ParkController {

    private final ParkService parkService;

    @PostMapping("/userId/{userId}/check-in")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @Operation(summary = "Check in to a park",
            description = "Allows a driver to check in to a park, recording the time and location.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful check-in",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request provided"),
                    @ApiResponse(responseCode = "403", description = "Not authorized to perform this action")
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    public CustomResponse<ParkCheckInResponse> checkIn(@PathVariable @UUID final String userId, @RequestBody @Valid ParkCheckInRequest parkCheckInRequest) {
        final ParkCheckInResponse parkCheckInResponse = parkService.checkIn(userId, parkCheckInRequest);
        return CustomResponse.ok(parkCheckInResponse);
    }

    @PostMapping("/userId/{userId}/check-out")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @Operation(summary = "Check out from a park",
            description = "Allows a driver to check out from a park, recording the departure time.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful check-out",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request provided"),
                    @ApiResponse(responseCode = "403", description = "Not authorized to perform this action")
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    public CustomResponse<ParkCheckOutResponse> checkOut(
            @PathVariable("userId") @UUID final String userId,
            @RequestBody @Valid ParkCheckOutRequest parkCheckOutRequest
    ) {
        final ParkCheckOutResponse parkCheckOutResponse = parkService
                .checkOut(userId, parkCheckOutRequest);

        return CustomResponse.ok(parkCheckOutResponse);
    }

}
