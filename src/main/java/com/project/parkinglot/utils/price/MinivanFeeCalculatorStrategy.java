package com.project.parkinglot.utils.price;

import java.math.BigDecimal;

/**
 * Strategy implementation class named {@link MinivanFeeCalculatorStrategy} for calculating parking fees for Minivan vehicles.
 */
public class MinivanFeeCalculatorStrategy implements FeeCalculationStrategy {

    private static final double MINIVAN_FEE_RATE = 0.15;

    /**
     * Calculates the total parking price for Minivan vehicles.
     *
     * @param price the base price
     * @return the total price including the fee
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal price) {

        BigDecimal fee = price.multiply(BigDecimal.valueOf(MINIVAN_FEE_RATE));

        return price.add(fee);

    }
}
