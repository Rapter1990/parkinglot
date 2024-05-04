package com.project.parkinglot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration named {@link ParkStatus} representing the status of a park.
 */
@Getter
@RequiredArgsConstructor
public enum ParkStatus {
    EMPTY,
    FULL
}
