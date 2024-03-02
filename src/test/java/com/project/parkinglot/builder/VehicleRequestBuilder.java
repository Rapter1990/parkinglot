package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.dto.request.Vehicle.VehicleRequest;
import com.project.parkinglot.model.enums.VehicleType;
import java.util.Random;



public class VehicleRequestBuilder extends BaseBuilder<VehicleRequest>{

    public VehicleRequestBuilder(){
        super(VehicleRequest.class);
    }

    public VehicleRequestBuilder withValidFields(){

        final Faker faker = new Faker();

        return this
                .withLicensePlate(this.generateRandomLicensePlate())
                .withVehicleType(this.generateRandomVehicleType());
    }

    public VehicleRequestBuilder withLicensePlate(
            final String licensePlate
    ){
        data.setLicensePlate(licensePlate);
        return this;
    }

    public VehicleRequestBuilder withVehicleType(
            final VehicleType vehicleType
    ){
        data.setVehicleType(vehicleType);
        return this;
    }

    private String generateRandomLicensePlate(){
        Faker faker = new Faker();
        Random rnd = new Random();
        String characters = "ABCDEFGHUJKLMNOPRSTUVZ";
        int memoryTag = faker.number().numberBetween(10,99);
        char[] generatedRandomChar = {characters.charAt(rnd.nextInt(22)),characters.charAt(rnd.nextInt(22))};
        String ageIdentifier = String.copyValueOf(generatedRandomChar);
        int generatedRandomLetter = faker.number().numberBetween(10000,999999);
        return  memoryTag + ageIdentifier+ generatedRandomLetter;
    }

    private VehicleType generateRandomVehicleType(){
        Random rnd = new Random();
        int randomValue = rnd.nextInt(VehicleType.values().length);
        return VehicleType.values()[randomValue];

    }
}
