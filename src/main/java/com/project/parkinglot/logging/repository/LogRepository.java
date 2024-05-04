package com.project.parkinglot.logging.repository;

import com.project.parkinglot.logging.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface named {@link LogRepository} for managing log entity persistence.
 */
public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
