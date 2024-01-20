package com.project.parkinglot.repository;

import com.project.parkinglot.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkRepository extends JpaRepository<Park, UUID> {
}
