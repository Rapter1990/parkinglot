package com.project.parkinglot.service.auth;

import com.project.parkinglot.security.model.entity.RefreshToken;
import com.project.parkinglot.security.model.entity.UserEntity;

import java.util.Optional;

/**
 * Service interface named {@link RefreshTokenService} for managing refresh tokens.
 */
public interface RefreshTokenService {

    /**
     * Creates a refresh token for the specified user.
     *
     * @param userEntity the user entity
     * @return the generated refresh token
     */
    String createRefreshToken(UserEntity userEntity);

    /**
     * Checks if the refresh token is expired.
     *
     * @param token the refresh token
     * @return {@code true} if the token is expired, {@code false} otherwise
     */
    boolean isRefreshExpired(RefreshToken token);

    /**
     * Retrieves the refresh token associated with the specified user.
     *
     * @param userId the user ID
     * @return the refresh token
     */
    RefreshToken getByUser(String userId);

    /**
     * Retrieves the refresh token by its token value.
     *
     * @param token the token value
     * @return an {@link Optional} containing the refresh token, or empty if not found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Deletes the refresh token associated with the specified user.
     *
     * @param userId the user ID
     * @return the number of refresh tokens deleted
     */
    int deleteByUserId(String userId);

}
