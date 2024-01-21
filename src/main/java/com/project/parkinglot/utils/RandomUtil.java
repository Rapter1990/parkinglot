package com.project.parkinglot.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class RandomUtil {

    private Random random = new Random();

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandomString() {
        return generateUUID().replace("-", "");
    }

    public static Integer generateRandomInteger(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static BigDecimal generateRandomBigDecimal(double min, double max) {

        final int scale = 2;
        double randomDouble = min + (max - min) * random.nextDouble();
        return new BigDecimal(randomDouble, new java.math.MathContext(scale));
    }

}

