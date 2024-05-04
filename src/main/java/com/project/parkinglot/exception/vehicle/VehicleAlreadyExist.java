package com.project.parkinglot.exception.vehicle;

import java.io.Serial;

/**
 * Exception class named {@link VehicleAlreadyExist} thrown when a user tries to add a vehicle that already exists.
 */
public class VehicleAlreadyExist extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 5431951469912355123L;

    private static final String DEFAULT_MESSAGE =
            "This User already has this vehicle";

    public VehicleAlreadyExist() {
        super(DEFAULT_MESSAGE);
    }

    public VehicleAlreadyExist(
            final String message
    ) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
