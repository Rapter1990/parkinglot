package com.project.parkinglot.controller;

import com.project.parkinglot.base.BaseControllerTest;
import com.project.parkinglot.builder.VehicleBuilder;
import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.park.ParkCheckInRequest;
import com.project.parkinglot.model.dto.request.vehicle.VehicleRequest;
import com.project.parkinglot.model.dto.response.ParkCheckInResponse;
import com.project.parkinglot.model.dto.response.VehicleCheckInResponse;
import com.project.parkinglot.model.enums.ParkStatus;
import com.project.parkinglot.service.park.ParkService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ParkControllerTest extends BaseControllerTest {

    @MockBean
    private ParkService parkService;

    @Test
    void givenUserIdAndParkCheckInRequest_whenCheckIn_thenReturnParkCheckInResponse() throws Exception {

        // Given
        final String mockUserId = UUID.randomUUID().toString();
        final String mockParkingAreaId = "parkingArea123";

        final Vehicle vehicle = new VehicleBuilder().withValidFields().build();

        final VehicleRequest vehicleRequest = VehicleRequest.builder()
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicle.getVehicleType())
                .build();

        final ParkCheckInRequest parkCheckInRequest = ParkCheckInRequest.builder()
                .parkingAreaId(mockParkingAreaId)
                .vehicle(vehicleRequest)
                .build();

        final ParkCheckInResponse parkCheckInResponse = ParkCheckInResponse.builder()
                .parkStatus(ParkStatus.FULL)
                .parkingAreaId(mockParkingAreaId)
                .checkIn(LocalDateTime.now())
                .vehicleCheckInResponse(VehicleCheckInResponse.builder()
                        .vehicleType(vehicleRequest.getVehicleType())
                        .licensePlate(vehicleRequest.getLicensePlate())
                        .build())
                .build();

        // When
        when(parkService.checkIn(mockUserId, parkCheckInRequest)).thenReturn(parkCheckInResponse);


        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/parks/userId/{userId}/check-in", mockUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(parkCheckInRequest))
                                .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.parkStatus")
                        .value(parkCheckInResponse.getParkStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.parkingAreaId")
                        .value(parkCheckInResponse.getParkingAreaId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.vehicleCheckInResponse.vehicleType")
                        .value(parkCheckInResponse.getVehicleCheckInResponse().getVehicleType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.vehicleCheckInResponse.licensePlate")
                        .value(parkCheckInResponse.getVehicleCheckInResponse().getLicensePlate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

        // Verify
        verify(parkService).checkIn(mockUserId, parkCheckInRequest);

    }

    @Test
    void givenUserIdAndParkCheckInRequest_whenUserTokenNotAvailable_thenReturnUnauthorized() throws Exception {

        // Given
        final String mockUserId = UUID.randomUUID().toString();
        final String mockParkingAreaId = "parkingArea123";

        final Vehicle vehicle = new VehicleBuilder().withValidFields().build();

        final VehicleRequest vehicleRequest = VehicleRequest.builder()
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicle.getVehicleType())
                .build();

        final ParkCheckInRequest parkCheckInRequest = ParkCheckInRequest.builder()
                .parkingAreaId(mockParkingAreaId)
                .vehicle(vehicleRequest)
                .build();

        final ParkCheckInResponse parkCheckInResponse = ParkCheckInResponse.builder()
                .parkStatus(ParkStatus.FULL)
                .parkingAreaId(mockParkingAreaId)
                .vehicleCheckInResponse(VehicleCheckInResponse.builder()
                        .vehicleType(vehicleRequest.getVehicleType())
                        .licensePlate(vehicleRequest.getLicensePlate())
                        .build())
                .build();

        // When
        when(parkService.checkIn(mockUserId, parkCheckInRequest)).thenReturn(parkCheckInResponse);


        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/parks/userId/{userId}/check-in", mockUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(parkCheckInRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        // Verify
        verify(parkService,Mockito.never()).checkIn(mockUserId, parkCheckInRequest);

    }

    @Test
    void givenUserIdAndParkCheckInRequest_whenAdminTokenUsage_thenReturnForbidden() throws Exception {

        // Given
        final String mockUserId = UUID.randomUUID().toString();
        final String mockParkingAreaId = "parkingArea123";

        final Vehicle vehicle = new VehicleBuilder().withValidFields().build();

        final VehicleRequest vehicleRequest = VehicleRequest.builder()
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicle.getVehicleType())
                .build();

        final ParkCheckInRequest parkCheckInRequest = ParkCheckInRequest.builder()
                .parkingAreaId(mockParkingAreaId)
                .vehicle(vehicleRequest)
                .build();

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/parks/userId/{userId}/check-in", mockUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(parkCheckInRequest))
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Verify
        verify(parkService,Mockito.never()).checkIn(mockUserId, parkCheckInRequest);

    }


}
