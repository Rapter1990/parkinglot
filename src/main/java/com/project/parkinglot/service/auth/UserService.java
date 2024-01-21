package com.project.parkinglot.service.auth;

import com.project.parkinglot.security.model.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

}
