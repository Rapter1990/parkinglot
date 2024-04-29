package com.project.parkinglot.controller;

import com.project.parkinglot.base.BaseControllerTest;
import com.project.parkinglot.payload.request.auth.LoginRequest;
import com.project.parkinglot.payload.request.auth.SignupRequest;
import com.project.parkinglot.payload.request.auth.TokenRefreshRequest;
import com.project.parkinglot.payload.response.auth.JWTResponse;
import com.project.parkinglot.payload.response.auth.TokenRefreshResponse;
import com.project.parkinglot.security.CustomUserDetails;
import com.project.parkinglot.security.model.entity.UserEntity;
import com.project.parkinglot.security.model.enums.Role;
import com.project.parkinglot.service.auth.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        Mockito.when(authService.register(request)).thenReturn("Success");

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Verify
        Mockito.verify(authService).register(request);

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
        Mockito.when(authService.register(request)).thenReturn("Success");

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Verify
        Mockito.verify(authService).register(request);

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
        Mockito.when(authService.login(request)).thenReturn(mockResponse);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        // Verify
        Mockito.verify(authService).login(request);

    }

    @Test
    void givenRefreshTokenRequestAndAccessToken_WhenCustomerRole_Token_ReturnRefreshTokenSuccess() throws Exception {

        // Given
        UserEntity mockUserEntity = UserEntity.builder()
                .id("1L")
                .username("driver_1")
                .email("driver_1@parkinglot.com")
                .role(Role.ROLE_DRIVER)
                .fullName("driver_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUserEntity);


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
        Mockito.when(authService.refreshToken(request)).thenReturn(mockResponse);
        Mockito.when(customUserDetailsService.loadUserByUsername("driver_1@parkinglot.com")).thenReturn(userDetails);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/refreshtoken")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        // Verify
        Mockito.verify(authService).refreshToken(request);
        Mockito.verify(customUserDetailsService).loadUserByUsername("driver_1@parkinglot.com");


    }

    @Test
    void givenAccessToken_WhenCustomerRole_ReturnLogoutSuccess() throws Exception {

        // Given
        UserEntity mockUserEntity = UserEntity.builder()
                .id("1L")
                .username("driver_1")
                .email("driver_1@parkinglot.com")
                .role(Role.ROLE_DRIVER)
                .fullName("driver_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUserEntity);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        // When
        Mockito.when(customUserDetailsService.loadUserByUsername("driver_1@parkinglot.com")).thenReturn(userDetails);
        Mockito.when(authService.logout(mockBearerToken)).thenReturn("Success");

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify
        Mockito.verify(customUserDetailsService).loadUserByUsername("driver_1@parkinglot.com");
        Mockito.verify(authService).logout(mockBearerToken);

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
        Mockito.when(authService.login(request)).thenReturn(mockResponse);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        // Verify
        Mockito.verify(authService).login(request);

    }

    @Test
    void givenRefreshTokenRequestandAccessToken_WhenAdminRole_Token_ReturnRefreshTokenSuccess() throws Exception {

        // Given
        UserEntity mockUserEntity = UserEntity.builder()
                .id("2L")
                .username("admin_1")
                .email("admin@parkinglot.com")
                .role(Role.ROLE_ADMIN)
                .fullName("admin_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUserEntity);


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
        Mockito.when(authService.refreshToken(request)).thenReturn(mockResponse);
        Mockito.when(customUserDetailsService.loadUserByUsername("admin@parkinglot.com")).thenReturn(userDetails);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/refreshtoken")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        // Verify
        Mockito.verify(authService).refreshToken(request);
        Mockito.verify(customUserDetailsService).loadUserByUsername("admin@parkinglot.com");

    }

    @Test
    void givenAccessToken_WhenAdminRole_ReturnLogoutSuccess() throws Exception {

        // Given
        UserEntity mockUserEntity = UserEntity.builder()
                .id("2L")
                .username("admin_1")
                .email("admin@parkinglot.com")
                .role(Role.ROLE_ADMIN)
                .fullName("admin_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUserEntity);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        // When
        Mockito.when(customUserDetailsService.loadUserByUsername("admin@parkinglot.com")).thenReturn(userDetails);
        Mockito.when(authService.logout(mockBearerToken)).thenReturn("Success");

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify
        Mockito.verify(customUserDetailsService).loadUserByUsername("admin@parkinglot.com");
        Mockito.verify(authService).logout(mockBearerToken);

    }

}