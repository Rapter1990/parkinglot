package com.project.parkinglot.utils.price;

import java.math.BigDecimal;

/**
 * Strategy implementation class named {@link SuvFeeCalculatorStrategy} for calculating parking fees for Suv vehicles.
 */
public class SuvFeeCalculatorStrategy implements FeeCalculationStrategy {

    private static final double SUV_FEE_RATE = 0.10;

    /**
     * Calculates the total parking price for Suv vehicles.
     *
     * @param price the base price
     * @return the total price including the fee
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal price) {

        BigDecimal fee = price.multiply(BigDecimal.valueOf(SUV_FEE_RATE));

        return price.add(fee);

    }


}
