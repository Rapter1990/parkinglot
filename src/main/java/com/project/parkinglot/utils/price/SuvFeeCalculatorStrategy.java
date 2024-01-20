package com.project.parkinglot.utils.price;

import java.math.BigDecimal;

public class SuvFeeCalculatorStrategy implements FeeCalculationStrategy {

    private static final double SUV_FEE_RATE = 0.10;

    @Override
    public BigDecimal calculatePrice(BigDecimal price) {

        BigDecimal fee = price.multiply(BigDecimal.valueOf(SUV_FEE_RATE));

        return price.add(fee);

    }


}
