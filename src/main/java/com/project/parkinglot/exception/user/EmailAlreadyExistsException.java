package com.project.parkinglot.exception.user;

import com.project.parkinglot.exception.AlreadyException;

import java.io.Serial;

/**
 * Exception class named {@link EmailAlreadyExistsException} thrown when an email already exists in the system.
 */
public class EmailAlreadyExistsException extends AlreadyException {

    @Serial
    private static final long serialVersionUID = -435147781709055915L;

    private static final String DEFAULT_MESSAGE =
            "The specified email already exists";

    private static final String MESSAGE_TEMPLATE =
            "Email already exists: ";

    public EmailAlreadyExistsException(String email) {
        super(MESSAGE_TEMPLATE.concat(email));
    }

    public EmailAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

}
