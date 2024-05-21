package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.builder.ParkingAreaEntityBuilder;
import com.project.parkinglot.builder.ParkingAreaUpdateRequestBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaUpdateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaMapper;
import com.project.parkinglot.repository.ParkingAreaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;


class ParkingAreaUpdateServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private ParkingAreaUpdateServiceImpl parkingAreaUpdateService;

    @Mock
    private ParkingAreaRepository parkingAreaRepository;

    private final ParkingAreaEntityToParkingAreaMapper parkingAreaEntityToParkingAreaMapper =
            ParkingAreaEntityToParkingAreaMapper.initialize();


    @Test
    void givenValidParkingAreaUpdateRequest_whenUpdateParkingArea_thenReturnParkingArea() {

        //Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaUpdateRequest mockParkingAreaUpdateRequest = new ParkingAreaUpdateRequestBuilder()
                .withValidFields()
                .build();

        final ParkingAreaEntity mockParkingAreaEntity = new ParkingAreaEntityBuilder()
                .withValidFields()
                .witId(mockParkingAreaId)
                .build();

        final ParkingArea mockBeforeUpdatedParkingArea = parkingAreaEntityToParkingAreaMapper.map(mockParkingAreaEntity);

        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId))
                .thenReturn(Optional.of(mockParkingAreaEntity));

        // Then
        final ParkingArea parkingAreaResponse =
                parkingAreaUpdateService.parkingAreaUpdateByCapacity(mockParkingAreaId, mockParkingAreaUpdateRequest);

        Assertions.assertNotNull(parkingAreaResponse);

        Assertions.assertEquals(
                mockParkingAreaUpdateRequest.getCapacity(),
                parkingAreaResponse.getCapacity()
        );

        Assertions.assertEquals(
                mockBeforeUpdatedParkingArea.getId(),
                parkingAreaResponse.getId()
        );

        Assertions.assertEquals(
                mockBeforeUpdatedParkingArea.getId(),
                parkingAreaResponse.getId()
        );

        Assertions.assertEquals(
                mockBeforeUpdatedParkingArea.getLocation(),
                parkingAreaResponse.getLocation()
        );

        Assertions.assertEquals(
                mockBeforeUpdatedParkingArea.getCity(),
                parkingAreaResponse.getCity()
        );

        // Verify
        Mockito.verify(parkingAreaRepository, Mockito.times(1)).findById(mockParkingAreaId);
        Mockito.verify(parkingAreaRepository, Mockito.times(1)).save(mockParkingAreaEntity);

    }

    @Test
    void givenEmptyParkingAreaUpdate_whenUpdateParkingArea_thenThrowParkingAreaNotFoundException() {

        // Given
        final ParkingAreaUpdateRequest mockParkingAreaUpdateRequest = new ParkingAreaUpdateRequestBuilder()
                .withValidFields()
                .build();

        final String mockParkingAreaUpdateRequestId = UUID.randomUUID().toString();

        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaUpdateRequestId)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(ParkingAreaNotFoundException.class,
                () -> parkingAreaUpdateService.parkingAreaUpdateByCapacity(mockParkingAreaUpdateRequestId, mockParkingAreaUpdateRequest));

        // Verify
        Mockito.verify(parkingAreaRepository, Mockito.times(1)).findById(mockParkingAreaUpdateRequestId);
        Mockito.verify(parkingAreaRepository, Mockito.times(0)).save(Mockito.any(ParkingAreaEntity.class));

    }

}
