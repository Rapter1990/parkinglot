package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.builder.ParkingAreaEntityBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.repository.ParkingAreaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ParkingAreaDeleteServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private ParkingAreaDeleteServiceImpl parkingAreaDeleteService;

    @Mock
    private ParkingAreaRepository parkingAreaRepository;

    @Test
    void givenMockParkingAreaIdAndParkingAreaEntity_whenParkingAreaFoundAndDelete_thenDeletedParkingAreaEntity() {

        // Given
        String mockParkingAreaId = UUID.randomUUID().toString();
        ParkingAreaEntity mockParkingArea = new ParkingAreaEntityBuilder()
                .withValidFields()
                .build();

        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId))
                .thenReturn(Optional.of(mockParkingArea));
        doNothing().when(parkingAreaRepository).delete(mockParkingArea);

        // Then
        parkingAreaDeleteService.deleteParkingAreaById(mockParkingAreaId);

        // Verify
        Mockito.verify(parkingAreaRepository, times(1))
                .findById(mockParkingAreaId);
        Mockito.verify(parkingAreaRepository, times(1))
                .delete(mockParkingArea);

    }

    @Test
    void givenNonExistentParkingAreaId_whenNotFoundParkingAreaById_throwsParkingAreaNotFoundException() {

        // Given
        String nonExistentParkingAreaId = UUID.randomUUID().toString();

        // When and Then
        ParkingAreaNotFoundException exception = Assertions.assertThrows(
                ParkingAreaNotFoundException.class,
                () -> parkingAreaDeleteService.deleteParkingAreaById(nonExistentParkingAreaId)
        );

        Assertions.assertEquals("No ParkingAreaEntity found with ID: Parking area not found with id: " +
                nonExistentParkingAreaId, exception.getMessage());

        // Verify
        verify(parkingAreaRepository, times(1))
                .findById(nonExistentParkingAreaId);
        verify(parkingAreaRepository, never())
                .delete(any());

    }

}