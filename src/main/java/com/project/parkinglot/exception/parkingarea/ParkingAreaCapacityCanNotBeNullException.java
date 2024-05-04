package com.project.parkinglot.exception.parkingarea;

import java.io.Serial;

/**
 * Exception class named {@link ParkingAreaCapacityCanNotBeNullException} thrown when the capacity of a parking area is null.
 */
public class ParkingAreaCapacityCanNotBeNullException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4523385870567922894L;

    private static final String DEFAULT_MESSAGE =
            "The Parking Area Capacity can not be null!";

    public ParkingAreaCapacityCanNotBeNullException(){ super(DEFAULT_MESSAGE);}

    public ParkingAreaCapacityCanNotBeNullException(
            final String message
    ){
        super(DEFAULT_MESSAGE + " " + message );
    }

}
