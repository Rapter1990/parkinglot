package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.model.enums.ParkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ParkRepository extends JpaRepository<ParkEntity, String> {

    Integer countByParkingAreaEntityAndParkStatus(ParkingAreaEntity parkingArea, ParkStatus parkStatus);

    Optional<ParkEntity> findByVehicleEntityAndParkStatus(VehicleEntity vehicle, ParkStatus parkStatus);

}
