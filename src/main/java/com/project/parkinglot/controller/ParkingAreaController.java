package com.project.parkinglot.controller;

import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaUpdateRequest;
import com.project.parkinglot.model.dto.response.parkingarea.ParkingAreaIncomeResponse;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.service.parking_area.ParkingAreaCreateService;
import com.project.parkinglot.service.parking_area.ParkingAreaDeleteService;
import com.project.parkinglot.service.parking_area.ParkingAreaGetService;
import com.project.parkinglot.service.parking_area.ParkingAreaUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/parking-area")
@RequiredArgsConstructor
@Validated
@Tag(name = "Parking Area Management", description = "APIs for managing parking areas")
public class ParkingAreaController {

    private final ParkingAreaUpdateService parkingAreaUpdateService;
    private final ParkingAreaCreateService parkingAreaCreateService;
    private final ParkingAreaDeleteService parkingAreaDeleteService;
    private final ParkingAreaGetService parkingAreaGetService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Create a new parking area",
            description = "Creates a new parking area with the specified details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parking area created successfully",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Not authorized to perform this action")
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    public CustomResponse<String> createParkingArea(@RequestBody @Valid final ParkingAreaCreateRequest parkingAreaCreateRequest) {

        final ParkingArea parkingArea = parkingAreaCreateService
                .createParkingArea(parkingAreaCreateRequest);

        return CustomResponse.ok(parkingArea.getId());

    }

    @GetMapping("/id/{parkingAreaId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get a parking area by ID",
            description = "Retrieves details of a parking area by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parking area found",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Parking area not found")
            })
    public CustomResponse<ParkingArea> getParkingAreaById(@PathVariable("parkingAreaId") @UUID final String parkingAreaId) {

        final ParkingArea parkingArea = parkingAreaGetService.getParkingAreaById(parkingAreaId);

        return CustomResponse.ok(parkingArea);

    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get a parking area by name",
            description = "Retrieves details of a parking area by its name.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parking area found",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Parking area not found")
            })
    public CustomResponse<ParkingArea> getParkingAreaByName(@PathVariable("name") @NotBlank final String name) {

        final ParkingArea parkingArea = parkingAreaGetService.getParkingAreaByName(name);

        return CustomResponse.ok(parkingArea);

    }

    @GetMapping("/income")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get daily income for a parking area",
            description = "Retrieves the daily income for a parking area on a specified date.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Income details retrieved successfully",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Parking area not found")
            })
    public CustomResponse<ParkingAreaIncomeResponse> getDailyIncome(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
            @RequestParam(value = "parkingAreaId") final String parkingAreaId
    ) {
        final ParkingAreaIncomeResponse dailyIncome = parkingAreaGetService.getDailyIncome(date, parkingAreaId);

        return CustomResponse.ok(dailyIncome);
    }

    @DeleteMapping("/{parkingAreaId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Delete a parking area by ID",
            description = "Deletes a parking area by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parking area deleted successfully",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Parking area not found")
            })
    public CustomResponse<String> deleteParkingAreaById(@PathVariable("parkingAreaId") @UUID final String parkingAreaId) {

        parkingAreaDeleteService.deleteParkingAreaById(parkingAreaId);

        return CustomResponse.ok("Parking area with id " + parkingAreaId + " is deleted");

    }

    @PutMapping("/{parkingAreaId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Update a parking area by ID",
            description = "Updates a parking area by its ID based on the provided data.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parking area updated successfully",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Parking area not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    public CustomResponse<String> updateParkingArea(
            @PathVariable("parkingAreaId") @UUID final String parkingAreaId,
            @RequestBody @Valid final ParkingAreaUpdateRequest parkingAreaUpdateRequest
    ) {
        final ParkingArea parkingArea = parkingAreaUpdateService
                .parkingAreaUpdateByCapacity(parkingAreaId, parkingAreaUpdateRequest);

        return CustomResponse.ok("Parking area with id " + parkingArea.getId() + " is updated");
    }

}
