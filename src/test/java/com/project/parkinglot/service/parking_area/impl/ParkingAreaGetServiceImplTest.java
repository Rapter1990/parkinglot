package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.builder.ParkingAreaEntityBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaDomainModelMapper;
import com.project.parkinglot.repository.ParkingAreaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParkingAreaGetServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private ParkingAreaGetServiceImpl parkingAreaService;

    @Mock
    private ParkingAreaRepository parkingAreaRepository;

    private final ParkingAreaEntityToParkingAreaDomainModelMapper parkingAreaEntityToParkingAreaDomainModelMapper =
            ParkingAreaEntityToParkingAreaDomainModelMapper.initialize();

    @Test
    void givenValidParkingArea_whenGetParkingAreaById_thenReturnExistingParkingArea() {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaEntity mockParkingAreaEntity = new ParkingAreaEntityBuilder()
                .withValidFields()
                .withId(mockParkingAreaId)
                .build();

        final ParkingArea mockParkingArea = parkingAreaEntityToParkingAreaDomainModelMapper.map(mockParkingAreaEntity);

        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId)).thenReturn(Optional.of(mockParkingAreaEntity));

        // Then
        final ParkingArea result = parkingAreaService.getParkingAreaById(mockParkingAreaId);

        Assertions.assertEquals(mockParkingArea.getId(), result.getId());
        Assertions.assertEquals(mockParkingArea.getParkList(), result.getParkList());
        Assertions.assertEquals(mockParkingArea.getName(), result.getName());
        Assertions.assertEquals(mockParkingArea.getCapacity(), result.getCapacity());
        Assertions.assertEquals(mockParkingArea.getDailyIncomeList(), result.getDailyIncomeList());
        Assertions.assertEquals(mockParkingArea.getLocation(), result.getLocation());

        // Verify
        Mockito.verify(parkingAreaRepository, Mockito.times(1)).findById(mockParkingAreaId);

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
        Mockito.verify(parkingAreaRepository, Mockito.times(1)).findById(Mockito.anyString());

    }

}
