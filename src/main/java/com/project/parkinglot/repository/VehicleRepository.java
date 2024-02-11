package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {

    Optional<VehicleEntity> findByLicensePlate(String licensePlate);

}
