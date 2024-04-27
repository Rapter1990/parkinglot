package com.project.parkinglot.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Random;

@UtilityClass
public class TestUtilityClass {

    public LocalDate generateRandomDate(){

        Random rnd = new Random();
        int year = rnd.nextInt(4) + 2020; // Years 2020 to 2023
        int month = rnd.nextInt(12) + 1; // Months 1 to 12

        // Calculate the number of days in the month
        int daysInMonth;
        if (month == 2) { // February, need to check for leap year
            if (year % 4 == 0) {
                if (year % 100 == 0) {
                    daysInMonth = (year % 400 == 0) ? 29 : 28;
                } else {
                    daysInMonth = 29;
                }
            } else {
                daysInMonth = 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30; // April, June, September, November
        } else {
            daysInMonth = 31; // All other months
        }

        int day = rnd.nextInt(daysInMonth) + 1; // Generate a valid day for the determined month and year

        return LocalDate.of(year, month, day);

    }

}
