package com.project.parkinglot.security.repository;

import com.project.parkinglot.security.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface named {@link UserRepository} for managing user entities.
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * Check if a user with a given email exists.
     *
     * @param email The email to check.
     * @return True if the user exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Find a user by their username.
     *
     * @param username The username.
     * @return The user entity.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Find a user by their email.
     *
     * @param email The email.
     * @return The user entity.
     */
    Optional<UserEntity> findByEmail(String email);

}
