package com.project.parkinglot.repository;

import com.project.parkinglot.entity.ParkingArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingAreaRepository extends JpaRepository<ParkingArea, String> {
}
