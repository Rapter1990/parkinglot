package com.project.parkinglot.service.auth.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.payload.request.auth.LoginRequest;
import com.project.parkinglot.payload.request.auth.SignupRequest;
import com.project.parkinglot.payload.request.auth.TokenRefreshRequest;
import com.project.parkinglot.payload.response.auth.JWTResponse;
import com.project.parkinglot.payload.response.auth.TokenRefreshResponse;
import com.project.parkinglot.security.CustomUserDetails;
import com.project.parkinglot.security.jwt.JwtUtils;
import com.project.parkinglot.security.model.entity.RefreshToken;
import com.project.parkinglot.security.model.entity.User;
import com.project.parkinglot.security.model.enums.Role;
import com.project.parkinglot.security.repository.UserRepository;
import com.project.parkinglot.service.auth.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void givenSignUpRequest_WhenDriverRole_ReturnSuccess() {

        // Given
        SignupRequest request = SignupRequest.builder()
                .fullName("driver_fullname")
                .password("driver_password")
                .username("driver_1")
                .email("driver_1@parkinglot.com")
                .role(Role.ROLE_DRIVER)
                .build();

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // When
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Then
        String result = authService.register(request);

        assertEquals("Success", result);

        // Verify
        verify(userRepository).save(any(User.class));
    }

    @Test
    void givenSignUpRequest_WhenDriverRoleAndEmailAlreadyExists_ReturnException() {

        // Given
        SignupRequest request = SignupRequest.builder()
                .fullName("driver_fullname")
                .password("driver_password")
                .username("driver_1")
                .email("driver_1@parkinglot.com")
                .role(Role.ROLE_DRIVER)
                .build();

        // When
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Then
        assertThrows(Exception.class, () -> authService.register(request));

        // Verify
        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    void givenLoginRequest_WhenDriverRole_ReturnSuccess() {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("driver_1@parkinglot.com")
                .password("driver_password")
                .build();

        User mockUser = User.builder()
                .email(request.getEmail())
                .fullName("Test User")
                .username("testuser")
                .password("hashedPassword")
                .role(Role.ROLE_DRIVER)
                .build();

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        // When
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        when(jwtUtils.generateJwtToken(mockAuthentication)).thenReturn("mockedToken");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));
        when(refreshTokenService.createRefreshToken(any(User.class)))
                .thenReturn("actualRefreshToken");

        // Then
        JWTResponse jwtResponse = authService.login(request);

        assertNotNull(jwtResponse);
        assertEquals(request.getEmail(), jwtResponse.getEmail());
        assertEquals("mockedToken", jwtResponse.getToken());
        assertEquals("actualRefreshToken", jwtResponse.getRefreshToken());

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateJwtToken(mockAuthentication);
        verify(userRepository).findByEmail(request.getEmail());
        verify(refreshTokenService).createRefreshToken(any(User.class));

    }

    @Test
    void givenLoginRequest_WhenDriverRole_ReturnRuntimeException() {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("driver_1@parkinglot.com")
                .password("driver_password")
                .build();

        // When
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        // Then
        assertThrows(RuntimeException.class, () -> authService.login(request));

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

    }

    @Test
    void givenTokenRefreshRequest_WhenDriverRole_ReturnSuccess() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .user(User.builder().id("1L").build())
                .build();

        // When
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(false);
        when(jwtUtils.generateJwtToken(any(CustomUserDetails.class))).thenReturn("newMockedToken");

        // Then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        assertNotNull(tokenRefreshResponse);
        assertEquals("newMockedToken", tokenRefreshResponse.getAccessToken());
        assertEquals("validRefreshToken", tokenRefreshResponse.getRefreshToken());

        // Verify
        verify(refreshTokenService).findByToken(request.getRefreshToken());
        verify(refreshTokenService).isRefreshExpired(refreshToken);
        verify(jwtUtils).generateJwtToken(any(CustomUserDetails.class));

    }

    @Test
    void givenTokenRefreshRequest_WhenDriverRole_ReturnRefreshTokenNotFound() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("invalidRefreshToken")
                .build();

        // When
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(Exception.class, () -> authService.refreshToken(request));

        // Verify
        verify(refreshTokenService).findByToken(request.getRefreshToken());

    }

    @Test
    void givenTokenRefreshRequest_WhenDriverRole_ReturnRefreshTokenExpired() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("expiredRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .user(User.builder().id("1L").build())
                .build();

        // When
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(true);

        // Then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        assertNull(tokenRefreshResponse);

        // Verify
        verify(refreshTokenService).findByToken(request.getRefreshToken());
        verify(refreshTokenService).isRefreshExpired(refreshToken);

    }

    @Test
    void givenValidAccessToken_WhenDriverRole_ReturnLogoutSuccess() {

        // Given
        String token = "validAuthToken";
        String userId = "1L";

        // When
        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getIdFromToken(token)).thenReturn(userId);

        // Then
        String result = authService.logout(token);

        assertEquals("Success", result);

        // Verify
        verify(refreshTokenService).deleteByUserId(userId);

    }

    @Test
    void givenInvalidAccessToken_WhenDriverRole_ReturnLogoutFailed() {

        // Given
        String token = "invalidAuthToken";

        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(null); // Invalid token

        // When
        String result = authService.logout(token);

        // Then
        assertEquals("Failed", result);

        // Verify
        verify(refreshTokenService, never()).deleteByUserId(anyString());

    }

    @Test
    void givenInvalidAccessToken_WhenDriverRole_ReturnLogoutInvalidJwtToken() {

        // Given
        String token = "invalidAuthToken";

        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(false);

        // When
        String result = authService.logout(token);

        // Then
        assertEquals("Failed", result);

        // Verify
        verify(refreshTokenService, never()).deleteByUserId(anyString());

    }

    @Test
    void givenSignUpRequest_WhenAdminRole_ReturnSuccess() {

        // Given
        SignupRequest request = SignupRequest.builder()
                .fullName("admin_fullname")
                .password("admin_password")
                .username("admin_1")
                .email("admin@parkinglot.com")
                .role(Role.ROLE_ADMIN)
                .build();

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // When
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Then
        String result = authService.register(request);

        assertEquals("Success", result);

        // Verify
        verify(userRepository).save(any(User.class));

    }

    @Test
    void givenSignUpRequest_WhenAdminRoleAndEmailAlreadyExists_ReturnException() {

        // Given
        SignupRequest request = SignupRequest.builder()
                .fullName("admin_fullname")
                .password("admin_password")
                .username("admin_1")
                .email("admin@parkinglot.com")
                .role(Role.ROLE_ADMIN)
                .build();

        // When
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Then
        assertThrows(Exception.class, () -> authService.register(request));

        // Verify
        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    void givenLoginRequest_WhenWhenAdminRole_ReturnSuccess() {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("admin@parkinglot.com")
                .password("admin_password")
                .build();

        User mockUser = User.builder()
                .email(request.getEmail())
                .fullName("Test User")
                .username("testuser")
                .password("hashedPassword")
                .role(Role.ROLE_ADMIN)
                .build();

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        // When
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        when(jwtUtils.generateJwtToken(mockAuthentication)).thenReturn("mockedToken");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));
        when(refreshTokenService.createRefreshToken(any(User.class)))
                .thenReturn("actualRefreshToken");

        // Then
        JWTResponse jwtResponse = authService.login(request);

        assertNotNull(jwtResponse);
        assertEquals(request.getEmail(), jwtResponse.getEmail());
        assertEquals("mockedToken", jwtResponse.getToken());
        assertEquals("actualRefreshToken", jwtResponse.getRefreshToken());

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateJwtToken(mockAuthentication);
        verify(userRepository).findByEmail(request.getEmail());
        verify(refreshTokenService).createRefreshToken(any(User.class));

    }

    @Test
    void givenLoginRequest_WhenAdminRole_ReturnRuntimeException() {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("admin@parkinglot.com")
                .password("admin_password")
                .build();

        // When
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        // Then
        assertThrows(RuntimeException.class, () -> authService.login(request));

        // Verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

    }

    @Test
    void givenTokenRefreshRequest_WhenAdminRole_ReturnSuccess() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .user(User.builder().id("1L").build())
                .build();

        // When
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(false);
        when(jwtUtils.generateJwtToken(any(CustomUserDetails.class))).thenReturn("newMockedToken");

        // Then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        assertNotNull(tokenRefreshResponse);
        assertEquals("newMockedToken", tokenRefreshResponse.getAccessToken());
        assertEquals("validRefreshToken", tokenRefreshResponse.getRefreshToken());

        // Verify
        verify(refreshTokenService).findByToken(request.getRefreshToken());
        verify(refreshTokenService).isRefreshExpired(refreshToken);
        verify(jwtUtils).generateJwtToken(any(CustomUserDetails.class));

    }

    @Test
    void givenTokenRefreshRequest_WhenAdminRole_ReturnRefreshTokenNotFound() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("invalidRefreshToken")
                .build();

        // When
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(Exception.class, () -> authService.refreshToken(request));

        // Verify
        verify(refreshTokenService).findByToken(request.getRefreshToken());

    }

    @Test
    void givenTokenRefreshRequest_WhenAdminRole_ReturnRefreshTokenExpired() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("expiredRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .user(User.builder().id("1L").build())
                .build();

        // When
        when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(true);

        // Then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        assertNull(tokenRefreshResponse);

        // Verify
        verify(refreshTokenService).findByToken(request.getRefreshToken());
        verify(refreshTokenService).isRefreshExpired(refreshToken);

    }

    @Test
    void givenValidAccessToken_WhenAdminRole_ReturnLogoutSuccess() {

        // Given
        String token = "validAuthToken";
        String userId = "1L";

        // When
        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getIdFromToken(token)).thenReturn(userId);

        // Then
        String result = authService.logout(token);

        assertEquals("Success", result);

        // Verify
        verify(refreshTokenService).deleteByUserId(userId);

    }

    @Test
    void givenInvalidAccessToken_WhenAdminRole_ReturnLogoutFailed() {

        // Given
        String token = "invalidAuthToken";

        // When
        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(null); // Invalid token

        // Then
        String result = authService.logout(token);

        assertEquals("Failed", result);

        // Verify
        verify(refreshTokenService, never()).deleteByUserId(anyString());

    }

    @Test
    void givenInvalidAccessToken_WhenAdminRole_ReturnLogoutInvalidJwtToken() {

        // Given
        String token = "invalidAuthToken";

        // When
        when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(false);

        // Then
        String result = authService.logout(token);

        assertEquals("Failed", result);

        // Verify
        verify(refreshTokenService, never()).deleteByUserId(anyString());

    }

}