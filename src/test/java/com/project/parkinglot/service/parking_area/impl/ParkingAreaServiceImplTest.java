package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.builder.model.entity.ParkingAreaEntityBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.repository.ParkingAreaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

public class ParkingAreaServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private ParkingAreaServiceImpl parkingAreaService;

    @Mock
    private ParkingAreaRepository parkingAreaRepository;

    @Test
    void givenValidParkingArea_whenGetParkingAreaById_thenReturnParkingAreaDomainModel(){

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaEntity mockParkingAreaEntity = new ParkingAreaEntityBuilder()
                .withValidFields()
                .withId(mockParkingAreaId)
                .build();

        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId))
                .thenReturn(Optional.of(mockParkingAreaEntity));

        // Then
        final ParkingArea parkingAreaDomainModel = parkingAreaService
                .getParkingAreaById(mockParkingAreaId);

        Assertions.assertEquals(
                parkingAreaDomainModel.getId(),
                mockParkingAreaId
        );

        Assertions.assertEquals(
                parkingAreaDomainModel.getName(),
                mockParkingAreaEntity.getName()
        );

        Assertions.assertEquals(
                parkingAreaDomainModel.getLocation(),
                mockParkingAreaEntity.getLocation()
        );

        Assertions.assertEquals(
                parkingAreaDomainModel.getCapacity(),
                mockParkingAreaEntity.getCapacity()
        );

        Assertions.assertEquals(
                parkingAreaDomainModel.getCity(),
                mockParkingAreaEntity.getCity()
        );

        // Verify
        Mockito.verify(
                parkingAreaRepository,
                Mockito.times(1)
        ).findById(mockParkingAreaId);


    }

    @Test
    void givenNonExistParkingArea_whenGetParkingAreaById_thenThrowsParkingAreaNotFoundException(){

        // When
        Mockito.when(parkingAreaRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        // Then
        Assertions.assertThrowsExactly(
                ParkingAreaNotFoundException.class,
                () -> parkingAreaService.getParkingAreaById(Mockito.anyString())
        );

        // Verify
        Mockito.verify(
                parkingAreaRepository,
                Mockito.times(1)
        ).findById(Mockito.anyString());


    }
}
