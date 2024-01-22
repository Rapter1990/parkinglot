package com.project.parkinglot.repository;

import com.project.parkinglot.security.model.entity.RefreshToken;
import com.project.parkinglot.security.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByUserId(Long userId);

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);

}
