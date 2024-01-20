package com.project.parkinglot.utils.price;

import java.math.BigDecimal;

public interface FeeCalculationStrategy {

    BigDecimal calculatePrice(BigDecimal price);

}
