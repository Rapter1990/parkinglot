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
import com.project.parkinglot.security.model.entity.UserEntity;
import com.project.parkinglot.security.model.enums.Role;
import com.project.parkinglot.security.repository.UserRepository;
import com.project.parkinglot.service.auth.RefreshTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

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

        UserEntity userEntity = UserEntity.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // When
        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);

        // Then
        String result = authService.register(request);

        Assertions.assertEquals("Success", result);

        // Verify
        Mockito.verify(userRepository).save(Mockito.any(UserEntity.class));
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
        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Then
        Assertions.assertThrows(Exception.class, () -> authService.register(request));

        // Verify
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserEntity.class));

    }

    @Test
    void givenLoginRequest_WhenDriverRole_ReturnSuccess() {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("driver_1@parkinglot.com")
                .password("driver_password")
                .build();

        UserEntity mockUserEntity = UserEntity.builder()
                .email(request.getEmail())
                .fullName("Test User")
                .username("testuser")
                .password("hashedPassword")
                .role(Role.ROLE_DRIVER)
                .build();

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        // When
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        Mockito.when(jwtUtils.generateJwtToken(mockAuthentication)).thenReturn("mockedToken");
        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUserEntity));
        Mockito.when(refreshTokenService.createRefreshToken(Mockito.any(UserEntity.class)))
                .thenReturn("actualRefreshToken");

        // Then
        JWTResponse jwtResponse = authService.login(request);

        Assertions.assertNotNull(jwtResponse);
        Assertions.assertEquals(request.getEmail(), jwtResponse.getEmail());
        Assertions.assertEquals("mockedToken", jwtResponse.getToken());
        Assertions.assertEquals("actualRefreshToken", jwtResponse.getRefreshToken());

        // Verify
        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtUtils).generateJwtToken(mockAuthentication);
        Mockito.verify(userRepository).findByEmail(request.getEmail());
        Mockito.verify(refreshTokenService).createRefreshToken(Mockito.any(UserEntity.class));

    }

    @Test
    void givenLoginRequest_WhenDriverRole_ReturnRuntimeException() {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("driver_1@parkinglot.com")
                .password("driver_password")
                .build();

        // When
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        // Then
        Assertions.assertThrows(RuntimeException.class, () -> authService.login(request));

        // Verify
        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

    }

    @Test
    void givenTokenRefreshRequest_WhenDriverRole_ReturnSuccess() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .userEntity(UserEntity.builder().id("1L").build())
                .build();

        // When
        Mockito.when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        Mockito.when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(false);
        Mockito.when(jwtUtils.generateJwtToken(Mockito.any(CustomUserDetails.class))).thenReturn("newMockedToken");

        // Then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        Assertions.assertNotNull(tokenRefreshResponse);
        Assertions.assertEquals("newMockedToken", tokenRefreshResponse.getAccessToken());
        Assertions.assertEquals("validRefreshToken", tokenRefreshResponse.getRefreshToken());

        // Verify
        Mockito.verify(refreshTokenService).findByToken(request.getRefreshToken());
        Mockito.verify(refreshTokenService).isRefreshExpired(refreshToken);
        Mockito.verify(jwtUtils).generateJwtToken(Mockito.any(CustomUserDetails.class));

    }

    @Test
    void givenTokenRefreshRequest_WhenDriverRole_ReturnRefreshTokenNotFound() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("invalidRefreshToken")
                .build();

        // When
        Mockito.when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(Exception.class, () -> authService.refreshToken(request));

        // Verify
        Mockito.verify(refreshTokenService).findByToken(request.getRefreshToken());

    }

    @Test
    void givenTokenRefreshRequest_WhenDriverRole_ReturnRefreshTokenExpired() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("expiredRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .userEntity(UserEntity.builder().id("1L").build())
                .build();

        // When
        Mockito.when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        Mockito.when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(true);

        // Then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        Assertions.assertNull(tokenRefreshResponse);

        // Verify
        Mockito.verify(refreshTokenService).findByToken(request.getRefreshToken());
        Mockito.verify(refreshTokenService).isRefreshExpired(refreshToken);

    }

    @Test
    void givenValidAccessToken_WhenDriverRole_ReturnLogoutSuccess() {

        // Given
        String token = "validAuthToken";
        String userId = "1L";

        // When
        Mockito.when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        Mockito.when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        Mockito.when(jwtUtils.getIdFromToken(token)).thenReturn(userId);

        // Then
        String result = authService.logout(token);

        Assertions.assertEquals("Success", result);

        // Verify
        Mockito.verify(refreshTokenService).deleteByUserId(userId);

    }

    @Test
    void givenInvalidAccessToken_WhenDriverRole_ReturnLogoutFailed() {

        // Given
        String token = "invalidAuthToken";

        Mockito.when(jwtUtils.extractTokenFromHeader(token)).thenReturn(null); // Invalid token

        // When
        String result = authService.logout(token);

        // Then
        Assertions.assertEquals("Failed", result);

        // Verify
        Mockito.verify(refreshTokenService, Mockito.never()).deleteByUserId(Mockito.anyString());

    }

    @Test
    void givenInvalidAccessToken_WhenDriverRole_ReturnLogoutInvalidJwtToken() {

        // Given
        String token = "invalidAuthToken";

        Mockito.when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        Mockito.when(jwtUtils.validateJwtToken(token)).thenReturn(false);

        // When
        String result = authService.logout(token);

        // Then
        Assertions.assertEquals("Failed", result);

        // Verify
        Mockito.verify(refreshTokenService, Mockito.never()).deleteByUserId(Mockito.anyString());

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

        UserEntity userEntity = UserEntity.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        // When
        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);

        // Then
        String result = authService.register(request);

        Assertions.assertEquals("Success", result);

        // Verify
        Mockito.verify(userRepository).save(Mockito.any(UserEntity.class));

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
        Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Then
        Assertions.assertThrows(Exception.class, () -> authService.register(request));

        // Verify
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserEntity.class));

    }

    @Test
    void givenLoginRequest_WhenWhenAdminRole_ReturnSuccess() {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("admin@parkinglot.com")
                .password("admin_password")
                .build();

        UserEntity mockUserEntity = UserEntity.builder()
                .email(request.getEmail())
                .fullName("Test User")
                .username("testuser")
                .password("hashedPassword")
                .role(Role.ROLE_ADMIN)
                .build();

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        // When
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        Mockito.when(jwtUtils.generateJwtToken(mockAuthentication)).thenReturn("mockedToken");
        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUserEntity));
        Mockito.when(refreshTokenService.createRefreshToken(Mockito.any(UserEntity.class)))
                .thenReturn("actualRefreshToken");

        // Then
        JWTResponse jwtResponse = authService.login(request);

        Assertions.assertNotNull(jwtResponse);
        Assertions.assertEquals(request.getEmail(), jwtResponse.getEmail());
        Assertions.assertEquals("mockedToken", jwtResponse.getToken());
        Assertions.assertEquals("actualRefreshToken", jwtResponse.getRefreshToken());

        // Verify
        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtUtils).generateJwtToken(mockAuthentication);
        Mockito.verify(userRepository).findByEmail(request.getEmail());
        Mockito.verify(refreshTokenService).createRefreshToken(Mockito.any(UserEntity.class));

    }

    @Test
    void givenLoginRequest_WhenAdminRole_ReturnRuntimeException() {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("admin@parkinglot.com")
                .password("admin_password")
                .build();

        // When
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        // Then
        Assertions.assertThrows(RuntimeException.class, () -> authService.login(request));

        // Verify
        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

    }

    @Test
    void givenTokenRefreshRequest_WhenAdminRole_ReturnSuccess() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .userEntity(UserEntity.builder().id("1L").build())
                .build();

        // When
        Mockito.when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        Mockito.when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(false);
        Mockito.when(jwtUtils.generateJwtToken(Mockito.any(CustomUserDetails.class))).thenReturn("newMockedToken");

        // Then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        Assertions.assertNotNull(tokenRefreshResponse);
        Assertions.assertEquals("newMockedToken", tokenRefreshResponse.getAccessToken());
        Assertions.assertEquals("validRefreshToken", tokenRefreshResponse.getRefreshToken());

        // Verify
        Mockito.verify(refreshTokenService).findByToken(request.getRefreshToken());
        Mockito.verify(refreshTokenService).isRefreshExpired(refreshToken);
        Mockito.verify(jwtUtils).generateJwtToken(Mockito.any(CustomUserDetails.class));

    }

    @Test
    void givenTokenRefreshRequest_WhenAdminRole_ReturnRefreshTokenNotFound() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("invalidRefreshToken")
                .build();

        // When
        Mockito.when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(Exception.class, () -> authService.refreshToken(request));

        // Verify
        Mockito.verify(refreshTokenService).findByToken(request.getRefreshToken());

    }

    @Test
    void givenTokenRefreshRequest_WhenAdminRole_ReturnRefreshTokenExpired() {

        // Given
        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("expiredRefreshToken")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("validRefreshToken")
                .userEntity(UserEntity.builder().id("1L").build())
                .build();

        // When
        Mockito.when(refreshTokenService.findByToken(request.getRefreshToken()))
                .thenReturn(Optional.of(refreshToken));
        Mockito.when(refreshTokenService.isRefreshExpired(refreshToken)).thenReturn(true);

        // Then
        TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(request);

        Assertions.assertNull(tokenRefreshResponse);

        // Verify
        Mockito.verify(refreshTokenService).findByToken(request.getRefreshToken());
        Mockito.verify(refreshTokenService).isRefreshExpired(refreshToken);

    }

    @Test
    void givenValidAccessToken_WhenAdminRole_ReturnLogoutSuccess() {

        // Given
        String token = "validAuthToken";
        String userId = "1L";

        // When
        Mockito.when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        Mockito.when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        Mockito.when(jwtUtils.getIdFromToken(token)).thenReturn(userId);

        // Then
        String result = authService.logout(token);

        Assertions.assertEquals("Success", result);

        // Verify
        Mockito.verify(refreshTokenService).deleteByUserId(userId);

    }

    @Test
    void givenInvalidAccessToken_WhenAdminRole_ReturnLogoutFailed() {

        // Given
        String token = "invalidAuthToken";

        // When
        Mockito.when(jwtUtils.extractTokenFromHeader(token)).thenReturn(null); // Invalid token

        // Then
        String result = authService.logout(token);

        Assertions.assertEquals("Failed", result);

        // Verify
        Mockito.verify(refreshTokenService, Mockito.never()).deleteByUserId(Mockito.anyString());

    }

    @Test
    void givenInvalidAccessToken_WhenAdminRole_ReturnLogoutInvalidJwtToken() {

        // Given
        String token = "invalidAuthToken";

        // When
        Mockito.when(jwtUtils.extractTokenFromHeader(token)).thenReturn(token);
        Mockito.when(jwtUtils.validateJwtToken(token)).thenReturn(false);

        // Then
        String result = authService.logout(token);

        Assertions.assertEquals("Failed", result);

        // Verify
        Mockito.verify(refreshTokenService, Mockito.never()).deleteByUserId(Mockito.anyString());

    }

}
