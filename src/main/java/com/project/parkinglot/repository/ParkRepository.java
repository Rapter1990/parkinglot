package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.ParkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkRepository extends JpaRepository<ParkEntity, String> {
}
