package com.project.parkinglot.exception.parkingarea;

import java.io.Serial;

public class DailyIncomeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6775490480723838768L;

    private static final String DEFAULT_MESSAGE =
            "No Daily Income information found for the given ID and Date";

    public DailyIncomeException() {
        super(DEFAULT_MESSAGE);
    }

    public DailyIncomeException(
            final String message
    ) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
