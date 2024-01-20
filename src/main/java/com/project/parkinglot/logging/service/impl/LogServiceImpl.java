package com.project.parkinglot.logging.service.impl;

import com.project.parkinglot.logging.entity.LogEntity;
import com.project.parkinglot.logging.repository.LogRepository;
import com.project.parkinglot.logging.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Override
    public void saveLogToDatabase(LogEntity logEntity) {

        logEntity.setTime(LocalDateTime.now());
        logRepository.save(logEntity);

    }
}
