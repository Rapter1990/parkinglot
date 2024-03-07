package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {

    Boolean existsByLicensePlate(
            final String licensePlate
    );

}
