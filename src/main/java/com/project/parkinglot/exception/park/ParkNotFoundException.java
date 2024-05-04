package com.project.parkinglot.exception.park;

import com.project.parkinglot.exception.NotFoundException;

import java.io.Serial;

/**
 * Exception class named {@link ParkNotFoundException} thrown when a park entity is not found.
 */
public class ParkNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = -2716997535675707393L;

    private static final String DEFAULT_MESSAGE =
            "The specified ParkEntity is not found!";

    private static final String MESSAGE_TEMPLATE =
            "No ParkEntity found with ID: ";

    public ParkNotFoundException(String id) {
        super(MESSAGE_TEMPLATE.concat(id));
    }

    public ParkNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
