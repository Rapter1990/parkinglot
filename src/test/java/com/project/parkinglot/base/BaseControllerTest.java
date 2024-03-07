package com.project.parkinglot.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.parkinglot.builder.UserEntityBuilder;
import com.project.parkinglot.logging.entity.LogEntity;
import com.project.parkinglot.logging.service.impl.LogServiceImpl;
import com.project.parkinglot.security.CustomUserDetails;
import com.project.parkinglot.security.CustomUserDetailsService;
import com.project.parkinglot.security.jwt.JwtUtils;
import com.project.parkinglot.security.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseControllerTest extends AbstractTestContainerConfiguration {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected CustomUserDetailsService customUserDetailsService;

    @MockBean
    protected LogServiceImpl logService;

    @Autowired
    protected JwtUtils jwtUtils;

    protected UserEntity mockUserEntity;

    protected String mockUserToken;

    protected UserEntity mockAdmin;

    protected String mockAdminToken;


    @BeforeEach
    protected void initializeAuth() {

        this.mockUserEntity = new UserEntityBuilder().customer().build();
        this.mockAdmin = new UserEntityBuilder().admin().build();

        final CustomUserDetails mockUserDetails = new CustomUserDetails(mockUserEntity);
        final CustomUserDetails mockAdminDetails = new CustomUserDetails(mockAdmin);

        this.mockUserToken = generateMockToken(mockUserDetails);
        this.mockAdminToken = generateMockToken(mockAdminDetails);

        when(customUserDetailsService.loadUserByUsername(mockUserEntity.getEmail())).thenReturn(mockUserDetails);
        when(customUserDetailsService.loadUserByUsername(mockAdmin.getEmail())).thenReturn(mockAdminDetails);
        doNothing().when(logService).saveLogToDatabase(any(LogEntity.class));

    }

    private String generateMockToken(CustomUserDetails details) {
        return "Bearer ".concat(jwtUtils.generateJwtToken(details));
    }
}
