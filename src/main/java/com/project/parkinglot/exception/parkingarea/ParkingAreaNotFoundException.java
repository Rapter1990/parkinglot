package com.project.parkinglot.exception.parkingarea;

import com.project.parkinglot.exception.NotFoundException;

import java.io.Serial;

public class ParkingAreaNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = -75325461433448105L;

    private static final String DEFAULT_MESSAGE =
            "The specified ParkingArea is not found!";

    private static final String MESSAGE_TEMPLATE =
            "No ParkingArea found with ID: ";

    public ParkingAreaNotFoundException(String id) {
        super(MESSAGE_TEMPLATE.concat(id));
    }

    public ParkingAreaNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
