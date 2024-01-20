package com.project.parkinglot.utils.price;

import java.math.BigDecimal;

public class MinivanFeeCalculatorStrategy implements FeeCalculationStrategy {

    private static final double MINIVAN_FEE_RATE = 0.15;

    @Override
    public BigDecimal calculatePrice(BigDecimal price) {

        BigDecimal fee = price.multiply(BigDecimal.valueOf(MINIVAN_FEE_RATE));

        return price.add(fee);
    }
}
