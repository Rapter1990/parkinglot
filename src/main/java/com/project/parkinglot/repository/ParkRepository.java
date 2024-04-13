package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.model.enums.ParkStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkRepository extends JpaRepository<ParkEntity, String> {

    Integer countByParkingAreaEntityAndParkStatus(
            final ParkingAreaEntity parkingArea,
            final ParkStatus parkStatus
    );

    Optional<ParkEntity> findTopByVehicleEntityAndParkStatusOrderByCheckInDesc(
            final VehicleEntity vehicle,
            final ParkStatus parkStatus
    );

}
