package com.project.parkinglot.utils.price;

import java.math.BigDecimal;

public class SedanFeeCalculatorStrategy implements FeeCalculationStrategy {

    private static final double SEDAN_FEE_RATE = 0;

    @Override
    public BigDecimal calculatePrice(BigDecimal price) {

        BigDecimal fee = price.multiply(BigDecimal.valueOf(SEDAN_FEE_RATE));

        return price.add(fee);
    }

}
