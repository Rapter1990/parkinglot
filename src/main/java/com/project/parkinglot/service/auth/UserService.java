package com.project.parkinglot.service.auth;

import com.project.parkinglot.security.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findById(String userId);

    Optional<UserEntity> findByEmail(String email);

}
