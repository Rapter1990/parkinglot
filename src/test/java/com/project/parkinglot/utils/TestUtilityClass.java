package com.project.parkinglot.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Random;

@UtilityClass
public class TestUtilityClass {

    public LocalDate generateRandomDate() {

        Random rnd = new Random();
        int year = rnd.nextInt(4) + 2020; // Years 2020 to 2023
        int month = rnd.nextInt(12) + 1; // Months 1 to 12

        // Calculate the number of days in the month
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        int day = rnd.nextInt(daysInMonth) + 1; // Generate a valid day for the determined month and year

        return LocalDate.of(year, month, day);

    }

}
