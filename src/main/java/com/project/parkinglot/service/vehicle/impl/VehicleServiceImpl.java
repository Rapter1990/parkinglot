package com.project.parkinglot.service.vehicle.impl;

import com.project.parkinglot.exception.user.UserNotFoundException;
import com.project.parkinglot.exception.vehicle.VehicleAlreadyExist;
import com.project.parkinglot.exception.vehicle.VehicleNotFoundException;
import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.vehicle.VehicleRequest;
import com.project.parkinglot.model.dto.response.ParkDetailResponse;
import com.project.parkinglot.model.dto.response.VehicleParkingDetailResponse;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.model.mapper.park.ParkEntityToParkDetailResponse;
import com.project.parkinglot.model.mapper.vehicle.VehicleEntityToVehicleMapper;
import com.project.parkinglot.model.mapper.vehicle.VehicleRequestToVehicleMapper;
import com.project.parkinglot.model.mapper.vehicle.VehicleToVehicleEntityMapper;
import com.project.parkinglot.repository.VehicleRepository;
import com.project.parkinglot.security.model.entity.UserEntity;
import com.project.parkinglot.service.auth.UserService;
import com.project.parkinglot.service.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation class named {@link VehicleServiceImpl} for managing vehicle operations.
 */
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    private final UserService userService;

    private final VehicleToVehicleEntityMapper vehicleToVehicleEntityMapper =
            VehicleToVehicleEntityMapper.initialize();

    private final ParkEntityToParkDetailResponse parkEntityToParkDetailResponse =
            ParkEntityToParkDetailResponse.initialize();

    private final VehicleRequestToVehicleMapper vehicleRequestToVehicleMapper =
            VehicleRequestToVehicleMapper.initialize();

    private final VehicleEntityToVehicleMapper vehicleEntityToVehicleMapper =
            VehicleEntityToVehicleMapper.initialize();

    /**
     * Assigns a vehicle to a user.
     *
     * @param id             the ID of the user
     * @param vehicleRequest the vehicle details
     * @return the assigned vehicle
     */
    @Override
    @Transactional
    public Vehicle assignVehicleToUser(final String id, final VehicleRequest vehicleRequest) {

        final UserEntity userEntity = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cant find user given id"));

        final Vehicle vehicle = vehicleRequestToVehicleMapper.map(vehicleRequest);

        final VehicleEntity vehicleEntity = assignUserToVehicle(userEntity, vehicle);

        return vehicleEntityToVehicleMapper.map(vehicleEntity);

    }

    /**
     * Checks if a vehicle with the given license plate already exists.
     *
     * @param vehicle the vehicle to check
     * @throws VehicleAlreadyExist if a vehicle with the same license plate already exists
     */
    private void existByLicensePlate(final Vehicle vehicle) {

        if (Boolean.TRUE.equals(vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate()))) {
            throw new VehicleAlreadyExist();
        }

    }

    /**
     * Assigns a user to a vehicle and saves the vehicle entity.
     *
     * @param userEntity the user entity to assign to the vehicle
     * @param vehicle    the vehicle to assign the user to
     * @return the persisted vehicle entity with the assigned user
     * @throws VehicleAlreadyExist if a vehicle with the same license plate already exists
     */
    private VehicleEntity assignUserToVehicle(final UserEntity userEntity, final Vehicle vehicle) {

        existByLicensePlate(vehicle);

        final VehicleEntity vehicleEntityToBePersist = vehicleToVehicleEntityMapper
                .map(vehicle);

        vehicleEntityToBePersist.setUserEntity(userEntity);

        vehicleRepository.save(vehicleEntityToBePersist);

        return vehicleEntityToBePersist;

    }

    /**
     * Assigns a vehicle to a user if not assigned already; otherwise, retrieves the assigned vehicle.
     *
     * @param userId         the ID of the user
     * @param vehicleRequest the vehicle details
     * @return the assigned or retrieved vehicle
     */
    @Override
    @Transactional
    public Vehicle assignOrGet(final String userId, final VehicleRequest vehicleRequest) {
        final Optional<VehicleEntity> existingVehicle = vehicleRepository.findByLicensePlate(vehicleRequest.getLicensePlate());
        final VehicleEntity vehicleEntity = existingVehicle.orElseGet(() -> {
            Vehicle assignedVehicleEntity = assignVehicleToUser(userId, vehicleRequest);
            return vehicleToVehicleEntityMapper.map(assignedVehicleEntity);
        });
        return vehicleEntityToVehicleMapper.map(vehicleEntity);
    }

    /**
     * Retrieves a vehicle entity by its license plate.
     *
     * @param licensePlate the license plate of the vehicle
     * @return the vehicle entity
     */
    @Override
    public VehicleEntity findByLicensePlate(final String licensePlate) {
        return vehicleRepository
                .findByLicensePlate(licensePlate)
                .orElseThrow(VehicleNotFoundException::new);
    }

    /**
     * Retrieves parking details of a vehicle by its license plate.
     *
     * @param licensePlate the license plate of the vehicle
     * @return the parking details of the vehicle
     */
    @Override
    public VehicleParkingDetailResponse getParkingDetails(final String licensePlate) {

        final VehicleEntity vehicleEntity = vehicleRepository.findByLicensePlate(licensePlate).orElseThrow(() -> new VehicleNotFoundException(licensePlate));

        final List<ParkDetailResponse> parkDetails = vehicleEntity.getParkEntities().stream()
                .map(parkEntityToParkDetailResponse::map)
                .collect(Collectors.toList());

        return VehicleParkingDetailResponse.builder()
                .licensePlate(vehicleEntity.getLicensePlate())
                .parkDetails(parkDetails)
                .build();

    }

}
