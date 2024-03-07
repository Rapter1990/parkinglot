package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.enums.VehicleType;
import com.project.parkinglot.security.model.entity.UserEntity;

import java.util.Random;
import java.util.UUID;

public class VehicleBuilder extends BaseBuilder<Vehicle>{

    public VehicleBuilder(){super(Vehicle.class);}

    public VehicleBuilder withValidFields(){
        return this
                .withId(UUID.randomUUID().toString())
                .withVehicleType(this.generateRandomVehicleType())
                .withLicensePlate(this.generateRandomLicensePlate());
    }

    public VehicleBuilder withId(final String Id){
        data.setId(Id);
        return this;
    }

    public VehicleBuilder withLicensePlate(final String licensePlate){
        data.setLicensePlate(licensePlate);
        return this;
    }

    public VehicleBuilder withVehicleType(final VehicleType vehicleType){
        data.setVehicleType(vehicleType);
        return this;
    }


    private String generateRandomLicensePlate(){
        Faker faker = new Faker();
        Random rnd = new Random();
        String characters = "ABCDEFGHUJKLMNOPRSTUVZ";
        int memoryTag = faker.number().numberBetween(10,99);
        char[] generatedRandomChars = {characters.charAt(rnd.nextInt(22)),characters.charAt(rnd.nextInt(22))};
        String ageIdentifier =String.copyValueOf(generatedRandomChars);
        int generatedRandomLetters = faker.number().numberBetween(1000,99999);
        return  memoryTag + ageIdentifier+ generatedRandomLetters;
    }

    private VehicleType generateRandomVehicleType(){
        Random rnd = new Random();
        int randomValue = rnd.nextInt(VehicleType.values().length);
        return VehicleType.values()[randomValue];
    }

}

