package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.builder.ParkingAreaEntityBuilder;
import com.project.parkinglot.builder.ParkingAreaIncomeResponseBuilder;
import com.project.parkinglot.exception.parkingarea.DailyIncomeException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.response.parkingarea.ParkingAreaIncomeResponse;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaMapper;
import com.project.parkinglot.repository.ParkingAreaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

class ParkingAreaGetServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private ParkingAreaGetServiceImpl parkingAreaService;

    @Mock
    private ParkingAreaRepository parkingAreaRepository;

    private final ParkingAreaEntityToParkingAreaMapper parkingAreaEntityToParkingAreaMapper =
            ParkingAreaEntityToParkingAreaMapper.initialize();

    @Test
    void givenValidParkingArea_whenGetParkingAreaById_thenReturnExistingParkingArea() {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaEntity mockParkingAreaEntity = new ParkingAreaEntityBuilder()
                .withValidFields()
                .withId(mockParkingAreaId)
                .build();

        final ParkingArea mockParkingArea = parkingAreaEntityToParkingAreaMapper.map(mockParkingAreaEntity);

        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId)).thenReturn(Optional.of(mockParkingAreaEntity));

        // Then
        final ParkingArea result = parkingAreaService.getParkingAreaById(mockParkingAreaId);

        Assertions.assertEquals(mockParkingArea.getId(), result.getId());
        Assertions.assertEquals(mockParkingArea.getParkList(), result.getParkList());
        Assertions.assertEquals(mockParkingArea.getName(), result.getName());
        Assertions.assertEquals(mockParkingArea.getCapacity(), result.getCapacity());
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

    @Test
    void givenValidParkingArea_whenGetParkingAreaByName_thenReturnExistingParkingArea() {

        // Given
        final ParkingAreaEntity mockParkingAreaEntity = new ParkingAreaEntityBuilder()
                .withValidFields()
                .build();

        final String mockParkingAreaName = mockParkingAreaEntity.getName();

        final ParkingArea mockParkingArea = parkingAreaEntityToParkingAreaMapper.map(mockParkingAreaEntity);

        // When
        Mockito.when(parkingAreaRepository.findByName(mockParkingAreaName)).thenReturn(Optional.of(mockParkingAreaEntity));

        // Then
        final ParkingArea result = parkingAreaService.getParkingAreaByName(mockParkingAreaName);

        Assertions.assertEquals(mockParkingArea.getId(), result.getId());
        Assertions.assertEquals(mockParkingArea.getParkList(), result.getParkList());
        Assertions.assertEquals(mockParkingArea.getName(), result.getName());
        Assertions.assertEquals(mockParkingArea.getCapacity(), result.getCapacity());
        Assertions.assertEquals(mockParkingArea.getLocation(), result.getLocation());

        // Verify
        Mockito.verify(parkingAreaRepository, Mockito.times(1)).findByName(mockParkingAreaName);

    }

    @Test
    void givenNonExistParkingArea_whenGetParkingAreaByName_thenThrowsParkingAreaNotFoundException(){

        // When
        Mockito.when(parkingAreaRepository.findByName(Mockito.anyString()))
                .thenReturn(Optional.empty());

        // Then
        Assertions.assertThrowsExactly(
                ParkingAreaNotFoundException.class,
                () -> parkingAreaService.getParkingAreaByName(Mockito.anyString())
        );

        // Verify
        Mockito.verify(parkingAreaRepository, Mockito.times(1)).findByName(Mockito.anyString());

    }
    @Test
    void givenValidGetDailyIncomeParameter_WhenGivenDailyIncome_ThenReturnParkingAreaIncomeResponse(){

        // Given
        String mockParkingAreaId = UUID.randomUUID().toString();

        ParkingAreaEntity mockParkingAreaEntity = new ParkingAreaEntityBuilder()
                .withValidFields()
                .withId(mockParkingAreaId)
                .build();

        ParkingAreaIncomeResponse mockParkingAreaIncomeResponse = new ParkingAreaIncomeResponseBuilder()
                .withValidFields()
                .withName(mockParkingAreaEntity.getName())
                .build();

        LocalDate mockDate = generateRandomDate();




        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId)).thenReturn(Optional.of(mockParkingAreaEntity));

        Mockito.when(parkingAreaRepository.calculateDailyIncome(mockDate,mockParkingAreaId)).thenReturn(Optional.of(BigDecimal.valueOf(Mockito.anyLong())));

        // Then
        ParkingAreaIncomeResponse checkParkingAreaIncomeResponse = parkingAreaService.getDailyIncome(mockDate,mockParkingAreaId);
        Assertions.assertEquals(checkParkingAreaIncomeResponse.getName(),mockParkingAreaIncomeResponse.getName());

        //Verify
        Mockito.verify(parkingAreaRepository,Mockito.times(1)).findById(mockParkingAreaId);
        Mockito.verify(parkingAreaRepository,Mockito.times(1)).calculateDailyIncome(mockDate,mockParkingAreaId);

    }
    @Test
    void givenValidGetDailyIncomeParameter_WhenGivenDailyIncome_ThenThrowsParkingAreaNotFoundException(){

        // Given
        String mockParkingAreaId = UUID.randomUUID().toString();

        LocalDate mockDate = generateRandomDate();

        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrowsExactly(ParkingAreaNotFoundException.class,
                ()->parkingAreaService.getDailyIncome(mockDate,mockParkingAreaId));

        // Verify
        Mockito.verify(parkingAreaRepository,Mockito.times(1)).findById(Mockito.anyString());

        Mockito.verify(parkingAreaRepository,Mockito.never())
                .calculateDailyIncome(Mockito.any(LocalDate.class),Mockito.anyString());
    }

    @Test
    void givenValidGetDailyIncomeParameter_WhenGivenDailyIncome_ThenThrowsDailyIncomeException(){

        // Given
        String mockParkingAreaId = UUID.randomUUID().toString();

        ParkingAreaEntity mockParkingAreaEntity = new ParkingAreaEntityBuilder()
                .withValidFields()
                .withId(mockParkingAreaId)
                .build();


        LocalDate mockDate = generateRandomDate();

        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId)).thenReturn(Optional.of(mockParkingAreaEntity));
        Mockito.when(parkingAreaRepository.calculateDailyIncome(mockDate,mockParkingAreaId)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrowsExactly(DailyIncomeException.class,
                ()->parkingAreaService.getDailyIncome(mockDate,mockParkingAreaId));

        // Verify
        Mockito.verify(parkingAreaRepository,Mockito.times(1)).findById(Mockito.anyString());

        Mockito.verify(parkingAreaRepository,Mockito.times(1))
                .calculateDailyIncome(Mockito.any(LocalDate.class),Mockito.anyString());
    }




    private LocalDate generateRandomDate(){

        Random rnd = new Random();

        return LocalDate.of(rnd.nextInt(4)+2020, rnd.nextInt(11)+1, rnd.nextInt(30)+1);
    }

}
