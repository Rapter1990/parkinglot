package com.project.parkinglot.service.vehicle.impl;

import com.project.parkinglot.exception.user.UserNotFoundException;
import com.project.parkinglot.exception.vehicle.VehicleAlreadyExist;
import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.Vehicle.VehicleRequest;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.model.mapper.vehicle.VehicleEntityToVehicleMapper;
import com.project.parkinglot.model.mapper.vehicle.VehicleToVehicleEntityMapper;
import com.project.parkinglot.model.mapper.vehicle.VehicleRequestToVehicleMapper;
import com.project.parkinglot.repository.VehicleRepository;
import com.project.parkinglot.security.model.entity.User;
import com.project.parkinglot.service.auth.UserService;
import com.project.parkinglot.service.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;//Kontrol et

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    private final UserService userService;

    private final VehicleToVehicleEntityMapper vehicleToVehicleEntityMapper =
            VehicleToVehicleEntityMapper.initialize();

    private final VehicleRequestToVehicleMapper vehicleRequestToVehicleMapper=
            VehicleRequestToVehicleMapper.initialize();

    private final VehicleEntityToVehicleMapper vehicleEntityToVehicleMapper =
            VehicleEntityToVehicleMapper.initialize();

    @Override
    @Transactional
    public Vehicle assignVehicleToUser(final String id, final VehicleRequest vehicleRequest) {

        final User user = userService.findById(id)
                .orElseThrow( ()-> new UserNotFoundException("Cant find user given id"));

        final Vehicle vehicle = vehicleRequestToVehicleMapper.map(vehicleRequest);

        final VehicleEntity vehicleEntity = assignUserToVehicle(user, vehicle);

        return vehicleEntityToVehicleMapper.map(vehicleEntity);

    }

    private void existByLicensePlate (final Vehicle vehicle){

        if (Boolean.TRUE.equals(vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate()))){

            throw new VehicleAlreadyExist();

        }

    }

    private VehicleEntity assignUserToVehicle(final User user,final Vehicle vehicle){

        existByLicensePlate(vehicle);

        final VehicleEntity vehicleEntityToBePersist = vehicleToVehicleEntityMapper
                .map(vehicle);

        vehicleEntityToBePersist.setUser(user);

        vehicleRepository.save(vehicleEntityToBePersist);

        return vehicleEntityToBePersist;
    }

}
