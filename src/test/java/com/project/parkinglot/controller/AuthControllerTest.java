package com.project.parkinglot.controller;

import com.project.parkinglot.base.BaseControllerTest;
import com.project.parkinglot.payload.request.auth.LoginRequest;
import com.project.parkinglot.payload.request.auth.SignupRequest;
import com.project.parkinglot.payload.request.auth.TokenRefreshRequest;
import com.project.parkinglot.payload.response.auth.JWTResponse;
import com.project.parkinglot.payload.response.auth.TokenRefreshResponse;
import com.project.parkinglot.security.CustomUserDetails;
import com.project.parkinglot.security.model.entity.User;
import com.project.parkinglot.security.model.enums.Role;
import com.project.parkinglot.service.auth.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends BaseControllerTest {

    @MockBean
    private AuthServiceImpl authService;


    @Test
    void givenSignupRequest_WhenCustomerRole_ReturnSuccess() throws Exception {

        // Given
        SignupRequest request = SignupRequest.builder()
                .fullName("driver_fullname")
                .password("driver_password")
                .username("driver_1")
                .email("driver_1@parkinglot.com")
                .role(Role.ROLE_DRIVER)
                .build();

        // When
        when(authService.register(request)).thenReturn("Success");

        // Then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Verify
        verify(authService).register(request);

    }

    @Test
    void givenSignupRequest_WhenAdminRole_ReturnSuccess() throws Exception {

        // Given
        SignupRequest request = SignupRequest.builder()
                .fullName("admin_fullname")
                .password("admin_password")
                .username("admin_1")
                .email("admin@parkinglot.com")
                .role(Role.ROLE_ADMIN)
                .build();

        // When
        when(authService.register(request)).thenReturn("Success");

        // Then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Verify
        verify(authService).register(request);

    }

    @Test
    void givenLoginRequest_WhenCustomerRole_ReturnSuccess() throws Exception {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("driver@parkinglot.com")
                .password("driver_password")
                .build();

        JWTResponse mockResponse = JWTResponse.builder()
                .email(request.getEmail())
                .token("mockedToken")
                .refreshToken("mockedRefreshToken")
                .build();

        // When
        when(authService.login(request)).thenReturn(mockResponse);

        // Then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Verify
        verify(authService).login(request);

    }

    @Test
    void givenRefreshTokenRequestAndAccessToken_WhenCustomerRole_Token_ReturnRefreshTokenSuccess() throws Exception {

        // Given
        User mockUser = User.builder()
                .id("1L")
                .username("driver_1")
                .email("driver_1@parkinglot.com")
                .role(Role.ROLE_DRIVER)
                .fullName("driver_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUser);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        TokenRefreshResponse mockResponse = TokenRefreshResponse.builder()
                .accessToken("newMockedToken")
                .refreshToken("validRefreshToken")
                .build();

        // When
        when(authService.refreshToken(request)).thenReturn(mockResponse);
        when(customUserDetailsService.loadUserByUsername("driver_1@parkinglot.com")).thenReturn(userDetails);

        // Then
        mockMvc.perform(post("/api/v1/auth/refreshtoken")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Verify
        verify(authService).refreshToken(request);
        verify(customUserDetailsService).loadUserByUsername("driver_1@parkinglot.com");


    }

    @Test
    void givenAccessToken_WhenCustomerRole_ReturnLogoutSuccess() throws Exception {

        // Given
        User mockUser = User.builder()
                .id("1L")
                .username("driver_1")
                .email("driver_1@parkinglot.com")
                .role(Role.ROLE_DRIVER)
                .fullName("driver_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUser);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        // When
        when(customUserDetailsService.loadUserByUsername("driver_1@parkinglot.com")).thenReturn(userDetails);
        when(authService.logout(mockBearerToken)).thenReturn("Success");

        // Then
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken))
                .andExpect(status().isOk());

        // Verify
        verify(customUserDetailsService).loadUserByUsername("driver_1@parkinglot.com");
        verify(authService).logout(mockBearerToken);

    }

    @Test
    void givenLoginRequest_WhenAdminRole_ReturnSuccess() throws Exception {

        // Given
        LoginRequest request = LoginRequest.builder()
                .email("admin@parkinglot.com")
                .password("admin_password")
                .build();

        JWTResponse mockResponse = JWTResponse.builder()
                .email(request.getEmail())
                .token("mockedToken")
                .refreshToken("mockedRefreshToken")
                .build();

        // When
        when(authService.login(request)).thenReturn(mockResponse);

        // Then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Verify
        verify(authService).login(request);

    }

    @Test
    void givenRefreshTokenRequestandAccessToken_WhenAdminRole_Token_ReturnRefreshTokenSuccess() throws Exception {

        // Given
        User mockUser = User.builder()
                .id("2L")
                .username("admin_1")
                .email("admin@parkinglot.com")
                .role(Role.ROLE_ADMIN)
                .fullName("admin_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUser);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        TokenRefreshResponse mockResponse = TokenRefreshResponse.builder()
                .accessToken("newMockedToken")
                .refreshToken("validRefreshToken")
                .build();

        // When
        when(authService.refreshToken(request)).thenReturn(mockResponse);
        when(customUserDetailsService.loadUserByUsername("admin@parkinglot.com")).thenReturn(userDetails);

        // Then
        mockMvc.perform(post("/api/v1/auth/refreshtoken")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Verify
        verify(authService).refreshToken(request);
        verify(customUserDetailsService).loadUserByUsername("admin@parkinglot.com");

    }

    @Test
    void givenAccessToken_WhenAdminRole_ReturnLogoutSuccess() throws Exception {

        // Given
        User mockUser = User.builder()
                .id("2L")
                .username("admin_1")
                .email("admin@parkinglot.com")
                .role(Role.ROLE_ADMIN)
                .fullName("admin_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUser);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        // When
        when(customUserDetailsService.loadUserByUsername("admin@parkinglot.com")).thenReturn(userDetails);
        when(authService.logout(mockBearerToken)).thenReturn("Success");

        // Then
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken))
                .andExpect(status().isOk());

        // Verify
        verify(customUserDetailsService).loadUserByUsername("admin@parkinglot.com");
        verify(authService).logout(mockBearerToken);

    }

}