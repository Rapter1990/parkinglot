package com.project.parkinglot.exception.parkingarea;

import java.io.Serial;

/**
 * Exception class named {@link ParkingAreaCapacityException} thrown when the capacity of a parking area is not available.
 */
public class ParkingAreaCapacityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1476311515189012697L;

    private static final String DEFAULT_MESSAGE =
            "The Parking Area Capacity is not available";

    public ParkingAreaCapacityException() {
        super(DEFAULT_MESSAGE);
    }

}
