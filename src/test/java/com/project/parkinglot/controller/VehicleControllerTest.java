package com.project.parkinglot.controller;

import com.project.parkinglot.base.BaseControllerTest;
import com.project.parkinglot.builder.VehicleRequestBuilder;
import com.project.parkinglot.model.Vehicle;
import com.project.parkinglot.model.dto.request.Vehicle.VehicleRequest;
import com.project.parkinglot.service.vehicle.impl.VehicleServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

 class VehicleControllerTest extends BaseControllerTest {

     @MockBean
     private VehicleServiceImpl vehicleService;

     @SneakyThrows
     @Test
     void givenValidVehicleRequest_whenAssignUserToVehicle_ThenReturnCustomResponse() {

         // Given
         final String mockUserId = UUID.randomUUID().toString();

         final VehicleRequest mockVehicleAssignRequest = new VehicleRequestBuilder()
                 .withValidFields()
                 .build();

         final Vehicle mockVehicleToCreated = Vehicle
                 .builder()
                 .licensePlate(mockVehicleAssignRequest.getLicensePlate())
                 .vehicleType(mockVehicleAssignRequest.getVehicleType())
                 .build();

         // When
         Mockito.when(
                 vehicleService.assignVehicleToUser(
                         Mockito.anyString(),
                         Mockito.any(VehicleRequest.class)
                 )
         ).thenReturn(mockVehicleToCreated);

         // Then
         mockMvc.perform(
                         MockMvcRequestBuilders
                                 .post("/api/v1/vehicles/assign/{user-id}", mockUserId)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(mockVehicleAssignRequest))
                                 .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                 )
                 .andDo(MockMvcResultHandlers.print())
                 .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                         .value(mockVehicleToCreated.getLicensePlate()))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

         // Verify
         Mockito.verify(vehicleService, Mockito.times(1))
                 .assignVehicleToUser(Mockito.anyString(), Mockito.any(VehicleRequest.class));

     }

     @Test
     @SneakyThrows
     void givenAssignVehicleToUserRequest_whenLicenseTypeIsNotValid_thenThrowBadRequest() {

         // Given
         final String mockUserId = UUID.randomUUID().toString();

         final VehicleRequest mockVehicleAssignRequest = new VehicleRequestBuilder()
                 .withValidFields()
                 .withLicensePlate("abcde29179387189")
                 .build();

         final Vehicle mockVehicleToBeCreated = Vehicle
                 .builder()
                 .licensePlate(mockVehicleAssignRequest.getLicensePlate())
                 .vehicleType(mockVehicleAssignRequest.getVehicleType())
                 .build();


         // Then
         mockMvc.perform(
                         MockMvcRequestBuilders
                                 .post("/api/v1/vehicles/assign/{user-id}", mockUserId)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(mockVehicleAssignRequest))
                                 .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                 )
                 .andDo(MockMvcResultHandlers.print())
                 .andExpect(MockMvcResultMatchers.status().isBadRequest());

         // Verify
         Mockito.verify(vehicleService, Mockito.never())
                 .assignVehicleToUser(Mockito.anyString(), Mockito.any(VehicleRequest.class));

     }

     @Test
     @SneakyThrows
     void givenAssignVehicleToUserRequest_whenUserIsUnAuthorized_thenReturnUnauthorized() {

         // Given
         final String mockUserId = UUID.randomUUID().toString();

         final VehicleRequest mockVehicleAssignRequest = new VehicleRequestBuilder()
                 .withValidFields()
                 .withLicensePlate("abcde29179387189")
                 .build();

         final Vehicle mockVehicleToBeCreated = Vehicle
                 .builder()
                 .licensePlate(mockVehicleAssignRequest.getLicensePlate())
                 .vehicleType(mockVehicleAssignRequest.getVehicleType())
                 .build();

         // When
         Mockito.when(vehicleService.assignVehicleToUser(
                 mockUserId,
                 mockVehicleAssignRequest
         )).thenReturn(mockVehicleToBeCreated);

         // Then
         mockMvc.perform(
                         MockMvcRequestBuilders
                                 .post("/api/v1/vehicles/assign/{user-id}", mockUserId)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(mockVehicleAssignRequest))
                 )
                 .andDo(MockMvcResultHandlers.print())
                 .andExpect(MockMvcResultMatchers.status().isUnauthorized());

         // Verify
         Mockito.verify(vehicleService,Mockito.never())
                 .assignVehicleToUser(Mockito.anyString(),Mockito.any(VehicleRequest.class));

     }

     @Test
     @SneakyThrows
     void givenInvalidVehicleType_whenAssignUserToVehicle_ThenReturnBadRequest(){

         // Given
         final String mockUserId = UUID.randomUUID().toString();

         final VehicleRequest mockVehicleAssignRequest = new VehicleRequestBuilder()
                 .withValidFields()
                 .withLicensePlate("abcde29179387189")
                 .build();

         final Vehicle mockVehicleToBeCreated = Vehicle
                 .builder()
                 .licensePlate(mockVehicleAssignRequest.getLicensePlate())
                 .vehicleType(mockVehicleAssignRequest.getVehicleType())
                 .build();

         // When
         Mockito.when(vehicleService.assignVehicleToUser(
                 mockUserId,
                 mockVehicleAssignRequest
         )).thenReturn(mockVehicleToBeCreated);

         // Then
         mockMvc.perform(
                         MockMvcRequestBuilders
                                 .post("/api/v1/vehicles/assign/{user-id}", mockUserId)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(mockVehicleAssignRequest))
                                 .header(HttpHeaders.AUTHORIZATION,mockAdminToken)
                 )
                 .andDo(MockMvcResultHandlers.print())
                 .andExpect(MockMvcResultMatchers.status().isBadRequest());

         // Verify
         Mockito.verify(vehicleService,Mockito.never())
                 .assignVehicleToUser(Mockito.anyString(),Mockito.any(VehicleRequest.class));

     }

 }
