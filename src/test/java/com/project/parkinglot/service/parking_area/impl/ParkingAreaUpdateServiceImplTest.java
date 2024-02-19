package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.builder.ParkingAreaEntityBuilder;
import com.project.parkinglot.builder.ParkingAreaUpdateRequestBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parkingArea.ParkingAreaUpdateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
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

    @Test
    void givenValidParkingAreaUpdateRequest_whenUpdateParkingArea_thenReturnParkingAreaDomainModel(){

        //Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaUpdateRequest mockParkingAreaUpdateRequest = new ParkingAreaUpdateRequestBuilder()
                .withValidField()
                .build();

        final ParkingAreaEntity mockParkingAreaEntity = new ParkingAreaEntityBuilder()
                .withValidFields()
                .witId(mockParkingAreaId)
                .build();

        //when
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId))
                .thenReturn(Optional.of(mockParkingAreaEntity));

        //Then
        final ParkingArea mockParkingArea =
                parkingAreaUpdateService.parkingAreaUpdateByCapacity(mockParkingAreaId,mockParkingAreaUpdateRequest);
        Assertions.assertNotNull(mockParkingArea);
        Assertions.assertEquals(mockParkingAreaUpdateRequest.getCapacity(), mockParkingArea.getCapacity());

        //Verify
        Mockito.verify(parkingAreaRepository,Mockito.times(1)).findById(mockParkingAreaId);
        Mockito.verify(parkingAreaRepository,Mockito.times(1)).save(mockParkingAreaEntity);
    }

    @Test
    void givenEmptyParkingAreaUpdate_whenUpdateParkingArea_thenThrowParkingAreaNotFoundException(){

        //Given
        final ParkingAreaUpdateRequest mockParkingAreaUpdateRequest = new ParkingAreaUpdateRequestBuilder()
                .withValidField()
                .build();

        final String mockParkingAreaUpdateRequestId = UUID.randomUUID().toString();

        //when
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaUpdateRequestId)).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(ParkingAreaNotFoundException.class,
                () -> parkingAreaUpdateService.parkingAreaUpdateByCapacity(mockParkingAreaUpdateRequestId,mockParkingAreaUpdateRequest));

        //Verify
        Mockito.verify(parkingAreaRepository,Mockito.times(1)).findById(mockParkingAreaUpdateRequestId);
        Mockito.verify(parkingAreaRepository,Mockito.times(0)).save(Mockito.any(ParkingAreaEntity.class));
    }

}
