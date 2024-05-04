package com.project.parkinglot.service.auth;

import com.project.parkinglot.payload.request.auth.LoginRequest;
import com.project.parkinglot.payload.request.auth.SignupRequest;
import com.project.parkinglot.payload.request.auth.TokenRefreshRequest;
import com.project.parkinglot.payload.response.auth.JWTResponse;
import com.project.parkinglot.payload.response.auth.TokenRefreshResponse;

/**
 * Service interface named {@link AuthService} for user authentication.
 */
public interface AuthService {

    /**
     * Registers a new user.
     *
     * @param request the signup request
     * @return the generated user ID
     */
    String register(SignupRequest request);

    /**
     * Authenticates a user.
     *
     * @param request the login request
     * @return the JWT response
     */
    JWTResponse login(LoginRequest request);

    /**
     * Refreshes the JWT token.
     *
     * @param request the token refresh request
     * @return the token refresh response
     */
    TokenRefreshResponse refreshToken(TokenRefreshRequest request);

    /**
     * Logs out the user.
     *
     * @param token the user's token
     * @return the result of the logout operation
     */
    String logout(String token);

}
