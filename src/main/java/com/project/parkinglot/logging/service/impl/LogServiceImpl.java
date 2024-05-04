package com.project.parkinglot.logging.service.impl;

import com.project.parkinglot.logging.entity.LogEntity;
import com.project.parkinglot.logging.repository.LogRepository;
import com.project.parkinglot.logging.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service named {@link LogServiceImpl} implementation for managing log entities.
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    /**
     * Saves a log entity to the database.
     *
     * @param logEntity The log entity to be saved
     */
    @Override
    public void saveLogToDatabase(LogEntity logEntity) {

        logEntity.setTime(LocalDateTime.now());
        logRepository.save(logEntity);

    }
}
