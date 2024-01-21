package com.project.parkinglot.service.auth;

import com.project.parkinglot.security.model.entity.RefreshToken;
import com.project.parkinglot.security.model.entity.User;

import java.util.Optional;

public interface RefreshTokenService {

    String createRefreshToken(User user);

    boolean isRefreshExpired(RefreshToken token);

    RefreshToken getByUser(Long userId);

    Optional<RefreshToken> findByToken(String token);

    int deleteByUserId(Long userId);

}
