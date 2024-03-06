package com.project.parkinglot.service.user.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.builder.UserEntityBuilder;
import com.project.parkinglot.builder.VehicleEntityBuilder;
import com.project.parkinglot.exception.user.UserNotFoundException;
import com.project.parkinglot.model.User;
import com.project.parkinglot.model.entity.VehicleEntity;
import com.project.parkinglot.model.mapper.user.UserEntityToUserMapper;
import com.project.parkinglot.security.model.entity.UserEntity;
import com.project.parkinglot.service.auth.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

class UserGetServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private UserGetServiceImpl userGetService;

    @Mock
    private UserService userService;

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    @Test
    void givenUser_whenGetUserById_thenReturnUser() {

        // Given
        final String mockUserId = UUID.randomUUID().toString();

        final VehicleEntity mockVehicleEntity = new VehicleEntityBuilder().withValidFields().build();

        final UserEntity mockUserEntity = new UserEntityBuilder()
                .withId(mockUserId)
                .customer()
                .withVehicle(mockVehicleEntity)
                .build();

        final User mockUser = userEntityToUserMapper.map(mockUserEntity);

        // When
        Mockito.when(userService.findById(mockUserId))
                .thenReturn(Optional.of(mockUserEntity));

        // Then
        final User expected = userGetService.getUserById(mockUserId);

        Assertions.assertEquals(mockUser.getId(),expected.getId());
        Assertions.assertEquals(mockUser.getUsername(),expected.getUsername());
        Assertions.assertEquals(mockUser.getEmail(),expected.getEmail());
        Assertions.assertEquals(mockUser.getRole(),expected.getRole());
        Assertions.assertEquals(mockUser.getFullName(),expected.getFullName());
        Assertions.assertEquals(mockUser.getVehicleList().size(),expected.getVehicleList().size());
        Assertions.assertEquals(mockUser.getVehicleList().get(0).getVehicleType(),expected.getVehicleList().get(0).getVehicleType());
        Assertions.assertEquals(mockUser.getVehicleList().get(0).getLicensePlate(),expected.getVehicleList().get(0).getLicensePlate());

        // Verify
        Mockito.verify(userService,Mockito.times(1)).findById(Mockito.anyString());

    }

    @Test
    void givenNonExistsUser_whenGetUserById_thenThrowsUserNotFoundException(){

        // Given
        final String mockGivenId = UUID.randomUUID().toString();

        // When
        Mockito.when(userService.findById(mockGivenId))
                .thenReturn(Optional.empty());

        // Then
        Assertions.assertThrowsExactly(
                UserNotFoundException.class,
                () -> userGetService.getUserById(mockGivenId)
        );

        // Verify
        Mockito.verify(userService,Mockito.times(1)).findById(Mockito.anyString());

    }

}