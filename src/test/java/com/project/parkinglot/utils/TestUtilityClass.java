package com.project.parkinglot.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Random;

@UtilityClass
public class TestUtilityClass {

    public LocalDate generateRandomDate(){

        Random rnd = new Random();

        return LocalDate.of(rnd.nextInt(4)+2020, rnd.nextInt(11)+1, rnd.nextInt(30)+1);
    }

}
