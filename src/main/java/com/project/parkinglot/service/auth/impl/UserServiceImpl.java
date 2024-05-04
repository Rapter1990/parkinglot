package com.project.parkinglot.service.auth.impl;

import com.project.parkinglot.security.model.entity.UserEntity;
import com.project.parkinglot.security.repository.UserRepository;
import com.project.parkinglot.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class named {@link UserServiceImpl} that provides user-related functionalities.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Retrieves a user by username.
     *
     * @param username the username
     * @return an {@link Optional} containing the user, or empty if not found
     */
    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId the user ID
     * @return an {@link Optional} containing the user, or empty if not found
     */
    @Override
    public Optional<UserEntity> findById(String userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieves a user by email.
     *
     * @param email the email address
     * @return an {@link Optional} containing the user, or empty if not found
     */
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
