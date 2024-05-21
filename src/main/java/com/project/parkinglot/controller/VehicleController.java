package com.project.parkinglot.controller;


import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.vehicle.VehicleRequest;
import com.project.parkinglot.model.dto.response.VehicleParkingDetailResponse;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.service.vehicle.VehicleService;
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

/**
 * Controller class named {@link VehicleController} for managing vehicles.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vehicles")
@Validated
@Tag(name = "Vehicle Management", description = "APIs for managing vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    /**
     * Assigns a vehicle based on the provided request to a specified user.
     *
     * @param userId         The ID of the user to whom the vehicle is to be assigned.
     * @param vehicleRequest The details of the vehicle to be assigned.
     * @return A CustomResponse indicating the success of the operation.
     */
    @PostMapping("/assign/{user-id}")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @Operation(summary = "Assign a vehicle to a user",
            description = "Assigns a vehicle based on the provided request to a specified user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicle assigned successfully",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized to access this endpoint")
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    public CustomResponse<String> assignVehicleToUser(
            @PathVariable("user-id") @UUID final String userId,
            @RequestBody @Valid final VehicleRequest vehicleRequest
    ) {
        final Vehicle vehicle = vehicleService.assignVehicleToUser(userId, vehicleRequest);

        return CustomResponse.ok(vehicle.getLicensePlate());
    }

    /**
     * Retrieves parking details for a vehicle using its license plate number.
     *
     * @param licensePlate The license plate number of the vehicle.
     * @return A CustomResponse containing the parking details of the vehicle.
     */
    @GetMapping("/get-parking-detail/{licensePlate}")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @Operation(summary = "Get parking details of a vehicle",
            description = "Retrieves parking details for a vehicle using its license plate number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved parking details",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized to access this endpoint")
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    public CustomResponse<VehicleParkingDetailResponse> getParkingDetails(@PathVariable final String licensePlate) {
        return CustomResponse.ok(vehicleService.getParkingDetails(licensePlate));
    }

}
