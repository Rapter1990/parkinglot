package com.project.parkinglot.exception.user;

import com.project.parkinglot.exception.NotFoundException;

import java.io.Serial;

public class UserNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = 4895291155273970971L;

    private static final String DEFAULT_MESSAGE =
            "The specified user is not found";

    private static final String MESSAGE_TEMPLATE =
            "No user was found with ID: ";

    public UserNotFoundException(Long id) {
        super(MESSAGE_TEMPLATE.concat(String.valueOf(id)));
    }

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
