package com.project.parkinglot.repository;

import com.project.parkinglot.entity.ParkingArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingAreaRepository extends JpaRepository<ParkingArea, UUID> {
}
