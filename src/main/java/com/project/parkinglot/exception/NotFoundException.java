package com.project.parkinglot.exception;


import org.springframework.http.HttpStatus;

import java.io.Serial;

public abstract class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -9077739335957202970L;

    public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    protected NotFoundException(String message) {
        super(message);
    }

}
