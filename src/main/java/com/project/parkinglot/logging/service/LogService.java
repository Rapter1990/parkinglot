package com.project.parkinglot.logging.service;

import com.project.parkinglot.logging.entity.LogEntity;

public interface LogService {

    void saveLogToDatabase(LogEntity logEntity);

}
