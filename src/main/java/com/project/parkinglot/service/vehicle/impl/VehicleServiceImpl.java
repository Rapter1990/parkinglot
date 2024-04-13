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

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    private final UserService userService;

    private final VehicleToVehicleEntityMapper vehicleToVehicleEntityMapper =
            VehicleToVehicleEntityMapper.initialize();

    private final ParkEntityToParkDetailResponse parkEntityToParkDetailResponse =
            ParkEntityToParkDetailResponse.initialize();

    private final VehicleRequestToVehicleMapper vehicleRequestToVehicleMapper=
            VehicleRequestToVehicleMapper.initialize();

    private final VehicleEntityToVehicleMapper vehicleEntityToVehicleMapper =
            VehicleEntityToVehicleMapper.initialize();

    @Override
    @Transactional
    public Vehicle assignVehicleToUser(final String id, final VehicleRequest vehicleRequest) {

        final UserEntity userEntity = userService.findById(id)
                .orElseThrow( ()-> new UserNotFoundException("Cant find user given id"));

        final Vehicle vehicle = vehicleRequestToVehicleMapper.map(vehicleRequest);

        final VehicleEntity vehicleEntity = assignUserToVehicle(userEntity, vehicle);

        return vehicleEntityToVehicleMapper.map(vehicleEntity);

    }

    private void existByLicensePlate (final Vehicle vehicle){

        if (Boolean.TRUE.equals(vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate()))){

            throw new VehicleAlreadyExist();

        }

    }

    private VehicleEntity assignUserToVehicle(final UserEntity userEntity, final Vehicle vehicle){

        existByLicensePlate(vehicle);

        final VehicleEntity vehicleEntityToBePersist = vehicleToVehicleEntityMapper
                .map(vehicle);

        vehicleEntityToBePersist.setUserEntity(userEntity);

        vehicleRepository.save(vehicleEntityToBePersist);

        return vehicleEntityToBePersist;

    }

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

    @Override
    public VehicleEntity findByLicensePlate(final String licensePlate) {
        return vehicleRepository
                .findByLicensePlate(licensePlate)
                .orElseThrow(VehicleNotFoundException::new);
    }

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
