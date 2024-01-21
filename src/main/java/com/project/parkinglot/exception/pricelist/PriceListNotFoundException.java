package com.project.parkinglot.exception.pricelist;

import com.project.parkinglot.exception.NotFoundException;

import java.io.Serial;

public class PriceListNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = -2516692732933542371L;

    private static final String DEFAULT_MESSAGE =
            "The specified PriceListEntity is not found!";

    private static final String MESSAGE_TEMPLATE =
            "No PriceListEntity found with ID: ";

    public PriceListNotFoundException(String id) {
        super(MESSAGE_TEMPLATE.concat(id));
    }

    public PriceListNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
