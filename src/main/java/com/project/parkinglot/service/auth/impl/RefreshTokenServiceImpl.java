package com.project.parkinglot.service.auth.impl;

import com.project.parkinglot.exception.user.UserNotFoundException;
import com.project.parkinglot.security.model.entity.RefreshToken;
import com.project.parkinglot.security.model.entity.UserEntity;
import com.project.parkinglot.security.repository.RefreshTokenRepository;
import com.project.parkinglot.service.auth.RefreshTokenService;
import com.project.parkinglot.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refrEshexpireMs}")
    Long expireSeconds;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;


    @Override
    public String createRefreshToken(UserEntity userEntity) {

        RefreshToken token = getByUser(userEntity.getId());

        if (token == null) {
            token = new RefreshToken();
            token.setUserEntity(userEntity);
        }

        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusSeconds(expireSeconds).atZone(ZoneOffset.UTC).toLocalDate());
        refreshTokenRepository.save(token);

        return token.getToken();
    }

    @Override
    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(LocalDate.now());
    }

    @Override
    public RefreshToken getByUser(String userId) {
        return refreshTokenRepository.findByUserEntityId(userId);
    }


    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    @Override
    @Transactional
    public int deleteByUserId(String userId) {

        UserEntity userEntity = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return refreshTokenRepository.deleteByUserEntity(userEntity);
    }

}
