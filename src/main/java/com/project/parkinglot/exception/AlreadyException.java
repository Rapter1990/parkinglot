package com.project.parkinglot.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * An abstract exception class named {@link AlreadyException} representing the scenario where an entity already exists.
 * This exception should be extended by specific exception classes for different entities.
 */
public abstract class AlreadyException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4118974949033371402L;

    public static final HttpStatus STATUS = HttpStatus.CONFLICT;

    protected AlreadyException(String message) {
        super(message);
    }

}
