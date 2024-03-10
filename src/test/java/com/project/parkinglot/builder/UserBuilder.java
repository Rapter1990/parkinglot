package com.project.parkinglot.builder;

import com.project.parkinglot.model.User;
import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.security.model.enums.Role;
import com.project.parkinglot.utils.RandomUtil;

import java.util.ArrayList;

public class UserBuilder extends BaseBuilder<User> {

    public UserBuilder() {
        super(User.class);
        data.setVehicleList(new ArrayList<>());
    }

    public UserBuilder customer() {
        return this
                .withId("1L")
                .withFullName(RandomUtil.generateRandomString())
                .withUsername(RandomUtil.generateRandomString())
                .withEmail(RandomUtil.generateRandomString().concat("@parkingalot.com"))
                .withRole(Role.ROLE_DRIVER)
                .withVehicle(new VehicleBuilder().withValidFields().build());
    }

    public UserBuilder admin() {
        return this.customer()
                .withRole(Role.ROLE_ADMIN);
    }

    public UserBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public UserBuilder withFullName(String fullName) {
        data.setFullName(fullName);
        return this;
    }

    public UserBuilder withUsername(String username) {
        data.setUsername(username);
        return this;
    }

    public UserBuilder withEmail(String email) {
        data.setEmail(email);
        return this;
    }

    public UserBuilder withRole(Role role) {
        data.setRole(role);
        return this;
    }

    public UserBuilder withVehicle(Vehicle vehicle) {
        data.getVehicleList().add(vehicle);
        return this;
    }
}
