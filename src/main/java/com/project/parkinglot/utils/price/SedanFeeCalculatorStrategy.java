package com.project.parkinglot.utils.price;

import java.math.BigDecimal;

/**
 * Strategy implementation class named {@link SedanFeeCalculatorStrategy} for calculating parking fees for Sedan vehicles.
 */
public class SedanFeeCalculatorStrategy implements FeeCalculationStrategy {

    private static final double SEDAN_FEE_RATE = 0;

    /**
     * Calculates the total parking price for Sedan vehicles.
     *
     * @param price the base price
     * @return the total price including the fee
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal price) {

        BigDecimal fee = price.multiply(BigDecimal.valueOf(SEDAN_FEE_RATE));

        return price.add(fee);

    }

}
