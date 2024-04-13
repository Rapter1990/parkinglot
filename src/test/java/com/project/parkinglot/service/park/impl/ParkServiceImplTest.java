package com.project.parkinglot.service.park.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.builder.ParkEntityBuilder;
import com.project.parkinglot.builder.ParkingAreaEntityBuilder;
import com.project.parkinglot.builder.VehicleBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaCapacityException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.Park;
import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.dto.request.park.ParkCheckOutRequest;
import com.project.parkinglot.model.dto.request.vehicle.VehicleRequest;
import com.project.parkinglot.model.dto.response.park.ParkCheckInResponse;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.enums.ParkStatus;
import com.project.parkinglot.model.mapper.park.ParkCheckInRequestToParkEntityMapper;
import com.project.parkinglot.model.mapper.park.ParkEntityToParkCheckOutResponseMapper;
import com.project.parkinglot.model.mapper.park.ParkEntityToParkMapper;
import com.project.parkinglot.model.mapper.park.ParkToParkCheckInResponseMapper;
import com.project.parkinglot.model.mapper.park.ParkToParkEntityMapper;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaToParkingAreaEntityMapper;
import com.project.parkinglot.model.mapper.vehicle.VehicleToVehicleEntityMapper;
import com.project.parkinglot.repository.ParkRepository;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.vehicle.VehicleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParkServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private ParkServiceImpl parkService;

    @Mock
    private ParkRepository parkRepository;

    @Mock
    private ParkingAreaRepository parkingAreaRepository;


    @Mock
    private VehicleService vehicleService;

    private final ParkCheckInRequestToParkEntityMapper parkCheckInRequestToParkEntityMapper = ParkCheckInRequestToParkEntityMapper.initialize();

    private final ParkEntityToParkMapper parkEntityToParkMapper = ParkEntityToParkMapper.initialize();

    private final ParkToParkEntityMapper parkToParkEntityMapper = ParkToParkEntityMapper.initialize();

    private final ParkToParkCheckInResponseMapper parkToParkCheckInResponseMapper = ParkToParkCheckInResponseMapper.initialize();

    private final VehicleToVehicleEntityMapper vehicleToVehicleEntityMapper = VehicleToVehicleEntityMapper.initialize();

    private final ParkingAreaToParkingAreaEntityMapper parkingAreaToParkingAreaEntityMapper = ParkingAreaToParkingAreaEntityMapper.initialize();

    private final ParkEntityToParkCheckOutResponseMapper parkEntityToParkCheckOutResponseMapper = ParkEntityToParkCheckOutResponseMapper.initialize();

    @Test
    void givenUserIdAndParkCheckInRequest_whenCheckIn_ReturnParkCheckInResponse() {

        // Given
        String mockUserId = "user123";
        String mockParkingAreaId = "parkingArea123";

        Vehicle vehicle = new VehicleBuilder().withValidFields().build();

        VehicleRequest vehicleRequest = VehicleRequest.builder()
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicle.getVehicleType())
                .build();

        ParkCheckInRequest parkCheckInRequest = ParkCheckInRequest.builder()
                .parkingAreaId(mockParkingAreaId)
                .vehicle(vehicleRequest)
                .build();

        ParkingAreaEntity existingParkingArea = new ParkingAreaEntityBuilder().withValidFields()
                .withId(parkCheckInRequest.getParkingAreaId())
                .build();

        ParkEntity parkEntity = new ParkEntityBuilder().withValidFields()
                .withId(mockParkingAreaId)
                .withVehicleEntity(vehicleToVehicleEntityMapper.map(vehicle))
                .withCheckIn()
                .build();

        Park mockPark = parkEntityToParkMapper.map(parkEntity);

        ParkCheckInResponse expected = parkToParkCheckInResponseMapper.map(mockPark);

        // When
        when(parkingAreaRepository.findById(parkCheckInRequest.getParkingAreaId())).thenReturn(Optional.ofNullable(existingParkingArea));
        when(vehicleService.assignOrGet(mockUserId, vehicleRequest)).thenReturn(vehicle);
        when(parkRepository.save(any(ParkEntity.class))).thenReturn(parkEntity);

        // Then
        ParkCheckInResponse result = parkService.checkIn(mockUserId, parkCheckInRequest);

        // Then
        assertEquals(expected.getParkingAreaId(), result.getParkingAreaId());
        assertEquals(expected.getVehicleCheckInResponse().getLicensePlate(), result.getVehicleCheckInResponse().getLicensePlate());
        assertEquals(expected.getVehicleCheckInResponse().getVehicleType(), result.getVehicleCheckInResponse().getVehicleType());
        assertEquals(expected.getParkStatus(), result.getParkStatus());
        assertEquals(expected.getCheckIn().toLocalTime(), result.getCheckIn().toLocalTime());

        // Verify
        verify(parkRepository).save(any(ParkEntity.class));

    }

    @Test
    void givenUserIdAndParkCheckInRequest_whenCheckIn_thenThrowParkingAreaCapacityException() {

        // Given
        String userId = "user123";
        String mockParkingAreaId = "parkingArea123";

        Vehicle vehicle = new VehicleBuilder().withValidFields().build();

        VehicleRequest vehicleRequest = VehicleRequest.builder()
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicle.getVehicleType())
                .build();

        ParkCheckInRequest parkCheckInRequest = ParkCheckInRequest.builder()
                .parkingAreaId(mockParkingAreaId)
                .vehicle(vehicleRequest)
                .build();

        ParkingAreaEntity existingParkingArea = new ParkingAreaEntityBuilder().withValidFields()
                .withId(parkCheckInRequest.getParkingAreaId())
                .build();

        ParkEntity parkEntity = new ParkEntityBuilder().withValidFields()
                .withVehicleEntity(vehicleToVehicleEntityMapper.map(vehicle))
                .build();

        // When
        when(parkingAreaRepository.findById(parkCheckInRequest.getParkingAreaId())).thenReturn(Optional.ofNullable(existingParkingArea));
        when(vehicleService.assignOrGet(userId, vehicleRequest)).thenReturn(vehicle);
        when(parkService.countCurrentParks(existingParkingArea)).thenReturn(existingParkingArea.getCapacity() + 1);

        // Then
        assertThrows(ParkingAreaCapacityException.class, () -> parkService.checkIn(userId, parkCheckInRequest));

        // Verify
        verify(parkRepository, never()).save(parkEntity);

    }

    @Test
    void givenInvalidParkingAreaId_whenCheckOut_thenThrowParkingAreaNotFoundException() {

        // Given
        String userId = "user123";
        String mockParkingAreaId = "parkingArea123";

        Vehicle vehicle = new VehicleBuilder().withValidFields().build();

        VehicleRequest vehicleRequest = VehicleRequest.builder()
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicle.getVehicleType())
                .build();

        ParkCheckOutRequest parkCheckOutRequest = ParkCheckOutRequest.builder()
                .parkingAreaId(mockParkingAreaId)
                .vehicleRequest(vehicleRequest)
                .build();

        // When
        when(parkingAreaRepository.findById(parkCheckOutRequest.getParkingAreaId())).thenReturn(Optional.empty());

        // Then
        assertThrows(ParkingAreaNotFoundException.class, () -> parkService.checkOut(userId, parkCheckOutRequest));

        // Verify
        verify(vehicleService, never()).findByLicensePlate(vehicleRequest.getLicensePlate());
        verify(parkRepository, never()).findTopByVehicleEntityAndParkStatusOrderByCheckInDesc(any(), any());
        verify(parkRepository, never()).save(any(ParkEntity.class));

    }


    @Test
    void givenParkingAreaEntity_whenCountByParkingAreaEntityAndParkStatus_thenReturnCurrentParks() {

        // Given
        ParkingAreaEntity mockParkingArea = new ParkingAreaEntityBuilder().withValidFields().build();
        int expected = 5;

        // When
        when(parkRepository.countByParkingAreaEntityAndParkStatus(mockParkingArea, ParkStatus.EMPTY)).thenReturn(expected);

        // Then
        int result = parkService.countCurrentParks(mockParkingArea);

        assertEquals(expected, result);

        // Verify
        verify(parkRepository).countByParkingAreaEntityAndParkStatus(mockParkingArea, ParkStatus.EMPTY);

    }

}
