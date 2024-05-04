package com.project.parkinglot.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

/**
 * Utility class named {@link RandomUtil} for generating random values.
 */
@UtilityClass
public class RandomUtil {

    private Random random = new Random();

    /**
     * Generates a random UUID.
     *
     * @return a random UUID as a string
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates a random string.
     *
     * @return a random string
     */
    public static String generateRandomString() {
        return generateUUID().replace("-", "");
    }

    /**
     * Generates a random integer within the specified range.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random integer within the specified range
     */
    public static Integer generateRandomInteger(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generates a random BigDecimal within the specified range.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (exclusive)
     * @return a random BigDecimal within the specified range
     */
    public static BigDecimal generateRandomBigDecimal(double min, double max) {

        final int scale = 2;
        double randomDouble = min + (max - min) * random.nextDouble();
        return new BigDecimal(randomDouble, new java.math.MathContext(scale));
    }

}

