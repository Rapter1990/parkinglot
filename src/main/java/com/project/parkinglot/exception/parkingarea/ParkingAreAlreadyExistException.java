package com.project.parkinglot.exception.parkingarea;

import java.io.Serial;

public class ParkingAreAlreadyExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4515385870402222793L;

    private static final String DEFAULT_MESSAGE =
            "The Parking Area Name and Location already exist!";

    public ParkingAreAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    public ParkingAreAlreadyExistException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
