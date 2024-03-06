package com.project.parkinglot.controller;


import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.Vehicle.VehicleRequest;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.service.vehicle.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vehicles")
@Validated
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/assign/{user-id}")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public CustomResponse<String>  assignVehicleToUser(
            @PathVariable("user-id") @UUID final String userId,
            @RequestBody @Valid final VehicleRequest vehicleRequest
    ){
        final Vehicle vehicle = vehicleService.assignVehicleToUser(userId, vehicleRequest);

        return  CustomResponse.ok(vehicle.getLicensePlate());
    }

}
