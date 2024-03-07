package com.project.parkinglot.security.repository;

import com.project.parkinglot.security.model.entity.RefreshToken;
import com.project.parkinglot.security.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    RefreshToken findByUserEntityId(String userId);

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUserEntity(UserEntity userEntity);

}
