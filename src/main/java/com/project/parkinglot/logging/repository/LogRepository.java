package com.project.parkinglot.logging.repository;

import com.project.parkinglot.logging.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
