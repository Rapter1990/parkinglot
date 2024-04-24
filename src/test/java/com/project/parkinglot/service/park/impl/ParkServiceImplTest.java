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
import com.project.parkinglot.model.entity.VehicleEntity;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

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
        Mockito.when(parkingAreaRepository.findById(parkCheckInRequest.getParkingAreaId())).thenReturn(Optional.ofNullable(existingParkingArea));
        Mockito.when(vehicleService.assignOrGet(mockUserId, vehicleRequest)).thenReturn(vehicle);
        Mockito.when(parkRepository.save(Mockito.any(ParkEntity.class))).thenReturn(parkEntity);

        // Then
        ParkCheckInResponse result = parkService.checkIn(mockUserId, parkCheckInRequest);

        // Then
        Assertions.assertEquals(expected.getParkingAreaId(), result.getParkingAreaId());
        Assertions.assertEquals(expected.getVehicleCheckInResponse().getLicensePlate(), result.getVehicleCheckInResponse().getLicensePlate());
        Assertions.assertEquals(expected.getVehicleCheckInResponse().getVehicleType(), result.getVehicleCheckInResponse().getVehicleType());
        Assertions.assertEquals(expected.getParkStatus(), result.getParkStatus());
        Assertions.assertEquals(expected.getCheckIn().toLocalTime(), result.getCheckIn().toLocalTime());

        // Verify
        Mockito.verify(parkRepository).save(Mockito.any(ParkEntity.class));

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
        Mockito.when(parkingAreaRepository.findById(parkCheckInRequest.getParkingAreaId())).thenReturn(Optional.ofNullable(existingParkingArea));
        Mockito.when(vehicleService.assignOrGet(userId, vehicleRequest)).thenReturn(vehicle);
        Mockito.when(parkService.countCurrentParks(existingParkingArea)).thenReturn(existingParkingArea.getCapacity() + 1);

        // Then
        Assertions.assertThrows(ParkingAreaCapacityException.class, () -> parkService.checkIn(userId, parkCheckInRequest));

        // Verify
        Mockito.verify(parkRepository, Mockito.never()).save(parkEntity);

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
        Mockito.when(parkingAreaRepository.findById(parkCheckOutRequest.getParkingAreaId())).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(ParkingAreaNotFoundException.class, () -> parkService.checkOut(userId, parkCheckOutRequest));

        // Verify
        Mockito.verify(vehicleService, Mockito.never()).findByLicensePlate(vehicleRequest.getLicensePlate());
        Mockito.verify(parkRepository, Mockito.never()).findTopByVehicleEntityAndParkStatusOrderByCheckInDesc(Mockito.any(), Mockito.any());
        Mockito.verify(parkRepository, Mockito.never()).save(Mockito.any(ParkEntity.class));

    }

    @Test
    void givenExistingVehicleAndNoFullParks_whenCheckOut_thenThrowParkingAreaNotFoundException() {

        // Given
        String userId = "user123";
        String mockParkingAreaId = "parkingArea123";

        Vehicle vehicle = new VehicleBuilder().withValidFields().build();
        VehicleEntity existingVehicleEntity = vehicleToVehicleEntityMapper.map(vehicle);

        ParkCheckOutRequest parkCheckOutRequest = ParkCheckOutRequest.builder()
                .parkingAreaId(mockParkingAreaId)
                .vehicleRequest(VehicleRequest.builder()
                        .licensePlate(vehicle.getLicensePlate())
                        .vehicleType(vehicle.getVehicleType())
                        .build())
                .build();

        ParkingAreaEntity existingParkingAreaEntity = new ParkingAreaEntityBuilder().withValidFields().withId(mockParkingAreaId).build();

        // When
        Mockito.when(parkingAreaRepository.findById(mockParkingAreaId)).thenReturn(Optional.of(existingParkingAreaEntity));
        Mockito.when(vehicleService.findByLicensePlate(vehicle.getLicensePlate())).thenReturn(existingVehicleEntity);
        Mockito.when(parkRepository.findTopByVehicleEntityAndParkStatusOrderByCheckInDesc(existingVehicleEntity, ParkStatus.FULL)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(ParkingAreaNotFoundException.class, () -> parkService.checkOut(userId, parkCheckOutRequest));

        // Verify
        Mockito.verify(parkRepository, Mockito.never()).save(Mockito.any(ParkEntity.class));

    }

    @Test
    void givenParkingAreaEntity_whenCountByParkingAreaEntityAndParkStatus_thenReturnCurrentParks() {

        // Given
        ParkingAreaEntity mockParkingArea = new ParkingAreaEntityBuilder().withValidFields().build();
        int expected = 5;

        // When
        Mockito.when(parkRepository.countByParkingAreaEntityAndParkStatus(mockParkingArea, ParkStatus.EMPTY)).thenReturn(expected);

        // Then
        int result = parkService.countCurrentParks(mockParkingArea);

        Assertions.assertEquals(expected, result);

        // Verify
        Mockito.verify(parkRepository).countByParkingAreaEntityAndParkStatus(mockParkingArea, ParkStatus.EMPTY);

    }

}
