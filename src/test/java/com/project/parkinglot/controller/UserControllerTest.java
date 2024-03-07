package com.project.parkinglot.controller;

import com.project.parkinglot.base.BaseControllerTest;
import com.project.parkinglot.builder.UserBuilder;
import com.project.parkinglot.builder.UserEntityBuilder;
import com.project.parkinglot.builder.VehicleEntityBuilder;
import com.project.parkinglot.builder.VehicleRequestBuilder;
import com.project.parkinglot.model.User;
import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.Vehicle.VehicleRequest;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.security.model.entity.UserEntity;
import com.project.parkinglot.service.user.UserGetService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.never;

class UserControllerTest extends BaseControllerTest {

    @MockBean
    private UserGetService userGetService;

    @Test
    @SneakyThrows
    void givenUser_whenUserGetById_thenReturnCustomResponseUser() {

        // Given
        final String mockUserId = UUID.randomUUID().toString();

        final User mockUser = new UserBuilder().customer().build();

        // When
        Mockito.when(userGetService.getUserById(mockUserId)).thenReturn(mockUser);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/user/{user-id}", mockUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(mockUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.fullName").value(mockUser.getFullName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.username").value(mockUser.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.email").value(mockUser.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.role").value(mockUser.getRole().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.vehicleList[0].id").value(mockUser.getVehicleList().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.vehicleList[0].licensePlate").value(mockUser.getVehicleList().get(0).getLicensePlate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.vehicleList[0].vehicleType").value(mockUser.getVehicleList().get(0).getVehicleType().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

        // Verify
        Mockito.verify(userGetService, Mockito.times(1)).getUserById(mockUserId);

    }

    @Test
    @SneakyThrows
    void givenUser_whenUserRoleIsAdmin_thenReturnForbidden() {

        // Given
        final String mockUserId = UUID.randomUUID().toString();

        final User mockUser = new UserBuilder().customer().build();

        // When
        Mockito.when(userGetService.getUserById(mockUserId)).thenReturn(mockUser);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/user/{user-id}", mockUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Verify
        Mockito.verify(userGetService, never()).getUserById(mockUserId);

    }

    @Test
    @SneakyThrows
    void givenUser_whenUserIsUnAuthorized_thenReturnUnauthorized() {

        // Given
        final String mockUserId = UUID.randomUUID().toString();

        final User mockUser = new UserBuilder().customer().build();

        // When
        Mockito.when(userGetService.getUserById(mockUserId)).thenReturn(mockUser);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/user/{user-id}", mockUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        // Verify
        Mockito.verify(userGetService, never()).getUserById(mockUserId);

    }

}
