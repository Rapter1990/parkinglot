package com.project.parkinglot.controller;

import com.project.parkinglot.payload.request.auth.LoginRequest;
import com.project.parkinglot.payload.request.auth.SignupRequest;
import com.project.parkinglot.payload.request.auth.TokenRefreshRequest;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.payload.response.auth.JWTResponse;
import com.project.parkinglot.payload.response.auth.TokenRefreshResponse;
import com.project.parkinglot.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for authentication operations such as register, login, refresh token, and logout.")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", description = "Registers a new user and returns a success message.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class)))
            })
    public CustomResponse<String> register(@RequestBody SignupRequest request) {

        return CustomResponse.created(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Logs in a user and returns JWT tokens.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged in successfully",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class)))
            })
    public CustomResponse<JWTResponse> login(@RequestBody LoginRequest request) {

        return CustomResponse.ok(authService.login(request));
    }

    @PostMapping("/refreshtoken")
    @Operation(summary = "Refresh token", description = "Refreshes the JWT authentication token using a refresh token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class)))
            })
    public CustomResponse<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {

        return CustomResponse.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user", description = "Logs out a user by invalidating the authentication token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged out successfully",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class)))
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    public CustomResponse<String> logout(@RequestHeader("Authorization") String token) {

        return CustomResponse.ok(authService.logout(token));
    }

}
