package com.project.parkinglot.builder;

import com.project.parkinglot.security.model.entity.User;
import com.project.parkinglot.security.model.enums.Role;
import com.project.parkinglot.utils.RandomUtil;

public class UserBuilder extends BaseBuilder<User> {

    public UserBuilder() {
        super(User.class);
    }

    public UserBuilder customer() {
        return this
                .withId("1L")
                .withFullName(RandomUtil.generateRandomString())
                .withUsername(RandomUtil.generateRandomString())
                .withEmail(RandomUtil.generateRandomString().concat("@parkingalot.com"))
                .withRole(Role.ROLE_DRIVER);
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

}
