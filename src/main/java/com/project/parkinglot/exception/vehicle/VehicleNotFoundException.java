package com.project.parkinglot.exception.vehicle;

import com.project.parkinglot.exception.NotFoundException;

import java.io.Serial;

public class VehicleNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = 5431951439973055123L;

    private static final String DEFAULT_MESSAGE =
            "The specified vehicle is not found";

    private static final String MESSAGE_TEMPLATE =
            "No vehicle was found with ID: ";

    public VehicleNotFoundException(String id) {
        super(MESSAGE_TEMPLATE.concat(id));
    }

    public VehicleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
