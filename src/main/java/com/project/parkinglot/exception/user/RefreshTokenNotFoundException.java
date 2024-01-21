package com.project.parkinglot.exception.user;

import com.project.parkinglot.exception.NotFoundException;

import java.io.Serial;

public class RefreshTokenNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = 7719065931723096729L;

    private static final String DEFAULT_MESSAGE =
            "The specified Refresh Token is not found!";

    public RefreshTokenNotFoundException(String message) {
        super(message);
    }

    public RefreshTokenNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
