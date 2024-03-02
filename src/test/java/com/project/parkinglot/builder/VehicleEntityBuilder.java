package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.model.enums.VehicleType;
import com.project.parkinglot.security.model.entity.User;

import java.util.Random;
import java.util.UUID;

public class VehicleEntityBuilder extends BaseBuilder<VehicleEntity>{

    public VehicleEntityBuilder(){super(VehicleEntity.class);}

    public VehicleEntityBuilder withValidFields(){
        Faker faker = new Faker();

        return this
                .withId(UUID.randomUUID().toString())
                .withVehicleType(this.generateRandomVehicleType())
                .withLicensePlate(this.generateRandomLicensePlate())
                .withUser(new UserBuilder()
                        .customer()
                        .build()
                );
    }

    public VehicleEntityBuilder withId(
            final String Id
    ){
        data.setId(Id);
        return this;
    }

    public VehicleEntityBuilder withLicensePlate(
            final String licensePlate
    ){
        data.setLicensePlate(licensePlate);
        return this;
    }

    public VehicleEntityBuilder withVehicleType(
            final VehicleType vehicleType
    ){
        data.setVehicleType(vehicleType);
        return this;
    }

    public VehicleEntityBuilder withUser(
            final User user
    ){
        data.setUser(user);
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
