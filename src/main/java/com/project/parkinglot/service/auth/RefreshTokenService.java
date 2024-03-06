package com.project.parkinglot.service.auth;

import com.project.parkinglot.security.model.entity.RefreshToken;
import com.project.parkinglot.security.model.entity.UserEntity;

import java.util.Optional;

public interface RefreshTokenService {

    String createRefreshToken(UserEntity userEntity);

    boolean isRefreshExpired(RefreshToken token);

    RefreshToken getByUser(String userId);

    Optional<RefreshToken> findByToken(String token);

    int deleteByUserId(String userId);

}
