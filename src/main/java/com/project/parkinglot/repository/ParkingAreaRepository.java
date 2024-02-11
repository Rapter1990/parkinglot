package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.ParkingAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingAreaRepository extends JpaRepository<ParkingAreaEntity, String> {

    boolean existsByName(String name);

    boolean existsParkingAreaEntitiesByNameAndLocation(
            final String name,
            final String location
    );

    Optional<ParkingAreaEntity> findByName(String name);

}
