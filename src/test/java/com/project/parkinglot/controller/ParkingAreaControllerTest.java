package com.project.parkinglot.controller;

import com.project.parkinglot.base.BaseControllerTest;
import com.project.parkinglot.builder.ParkingAreaCreateRequestBuilder;
import com.project.parkinglot.builder.ParkingAreaEntityBuilder;
import com.project.parkinglot.builder.ParkingAreaUpdateRequestBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parkingArea.ParkingAreaUpdateRequest;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaCreateRequestToParkingAreaEntityMapper;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaDomainModelMapper;
import com.project.parkinglot.service.parking_area.ParkingAreaUpdateService;
import com.project.parkinglot.service.parking_area.impl.ParkingAreaCreateServiceImpl;
import com.project.parkinglot.service.parking_area.impl.ParkingAreaDeleteServiceImpl;
import com.project.parkinglot.service.parking_area.impl.ParkingAreaUpdateServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;


public class ParkingAreaControllerTest extends BaseControllerTest {

    @MockBean
    private ParkingAreaCreateServiceImpl parkingAreaCreateService;

    @MockBean
    private ParkingAreaDeleteServiceImpl parkingAreaDeleteService;

    @MockBean
    private ParkingAreaUpdateServiceImpl parkingAreaUpdateService;

    private final ParkingAreaCreateRequestToParkingAreaEntityMapper parkingAreaCreateRequestToParkingAreaEntityMapper =
            ParkingAreaCreateRequestToParkingAreaEntityMapper.initialize();

    private final ParkingAreaEntityToParkingAreaDomainModelMapper parkingAreaEntityToParkingAreaDomainModelMapper =
            ParkingAreaEntityToParkingAreaDomainModelMapper.initialize();

    @Test
     void givenValidParkingAreaCreateRequest_whenParkingAreaCreated_thenReturnCustomResponse() throws Exception {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaCreateRequest mockParkingAreaCreateRequest = new ParkingAreaCreateRequestBuilder()
                .withValidFields()
                .build();

        final ParkingAreaEntity mockParkingAreaEntity = parkingAreaCreateRequestToParkingAreaEntityMapper
                .map(mockParkingAreaCreateRequest);

        mockParkingAreaEntity.setId(mockParkingAreaId);

        final ParkingArea mockParkingArea = parkingAreaEntityToParkingAreaDomainModelMapper
                .map(mockParkingAreaEntity);

        // When
        Mockito.when(parkingAreaCreateService.createParkingArea(Mockito.any(ParkingAreaCreateRequest.class)))
                .thenReturn(mockParkingArea);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/parking-area")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockParkingAreaCreateRequest))
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(mockParkingArea.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

        // Verify
        Mockito.verify(parkingAreaCreateService, times(1))
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }

    @Test
     void givenValidParkingAreaCreateRequest_whenUserUnauthorized_thenReturnForbidden() throws Exception {

        // Given
        final ParkingAreaCreateRequest mockParkingAreaCreateRequest = new ParkingAreaCreateRequestBuilder()
                .withValidFields()
                .build();

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/parking-area")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockParkingAreaCreateRequest))
                                .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Verify
        Mockito.verify(parkingAreaCreateService, never())
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }


    @Test
    public void givenInvalidParkingAreaCreateRequest_whenParkingAreaCapacityLessThanZero_thenReturnBadRequest() throws Exception {

        // Given
        final ParkingAreaCreateRequest mockParkingAreaCreateRequest = new ParkingAreaCreateRequestBuilder()
                .withValidFields()
                .withCapacity(-1)
                .build();

        final ParkingAreaEntity mockParkingAreaEntity = parkingAreaCreateRequestToParkingAreaEntityMapper
                .map(mockParkingAreaCreateRequest);

        final ParkingArea mockParkingArea = parkingAreaEntityToParkingAreaDomainModelMapper
                .map(mockParkingAreaEntity);

        // When
        Mockito.when(parkingAreaCreateService.createParkingArea(Mockito.any(ParkingAreaCreateRequest.class)))
                .thenReturn(mockParkingArea);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/parking-area")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockParkingAreaCreateRequest))
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Verify
        Mockito.verify(parkingAreaCreateService, never())
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }

    @Test
    public void givenInvalidParkingAreaCreateRequest_whenParkingAreaCapacityIsNull_thenReturnBadRequest() throws Exception {

        // Given
        final ParkingAreaCreateRequest mockParkingAreaCreateRequest = new ParkingAreaCreateRequestBuilder()
                .withValidFields()
                .withCapacity(null)
                .build();

        final ParkingAreaEntity mockParkingAreaEntity = parkingAreaCreateRequestToParkingAreaEntityMapper
                .map(mockParkingAreaCreateRequest);

        final ParkingArea mockParkingArea = parkingAreaEntityToParkingAreaDomainModelMapper
                .map(mockParkingAreaEntity);

        // When
        Mockito.when(parkingAreaCreateService.createParkingArea(Mockito.any(ParkingAreaCreateRequest.class)))
                .thenReturn(mockParkingArea);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/parking-area")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockParkingAreaCreateRequest))
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Verify
        Mockito.verify(parkingAreaCreateService, never())
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }

    @Test
    public void givenInvalidParkingAreaCreateRequest_whenParkingAreaNameIsNull_thenReturnBadRequest() throws Exception {

        // Given
        final ParkingAreaCreateRequest mockParkingAreaCreateRequest = new ParkingAreaCreateRequestBuilder()
                .withValidFields()
                .withName(null)
                .build();

        final ParkingAreaEntity mockParkingAreaEntity = parkingAreaCreateRequestToParkingAreaEntityMapper
                .map(mockParkingAreaCreateRequest);

        final ParkingArea mockParkingArea = parkingAreaEntityToParkingAreaDomainModelMapper
                .map(mockParkingAreaEntity);

        // When
        Mockito.when(parkingAreaCreateService.createParkingArea(Mockito.any(ParkingAreaCreateRequest.class)))
                .thenReturn(mockParkingArea);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/parking-area")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockParkingAreaCreateRequest))
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Verify
        Mockito.verify(parkingAreaCreateService, never())
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }

    @Test
     void givenValidParkingAreaId_whenParkingAreaDeleted_thenReturnCustomResponse() throws Exception {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        // When
        Mockito.doNothing().when(parkingAreaDeleteService).deleteParkingAreaById(mockParkingAreaId);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/parking-area/{id}", mockParkingAreaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value("Parking area with id " + mockParkingAreaId + " is deleted"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

        // Verify
        Mockito.verify(parkingAreaDeleteService, times(1))
                .deleteParkingAreaById(mockParkingAreaId);

    }

    @Test
     void givenInvalidParkingAreaId_whenParkingAreaDeleted_thenReturnNotFound() throws Exception {

        // Given
        String invalidParkingAreaId = UUID.randomUUID().toString();

        // When
        Mockito.doThrow(new ParkingAreaNotFoundException("Parking area not found with id: " + invalidParkingAreaId))
                .when(parkingAreaDeleteService).deleteParkingAreaById(invalidParkingAreaId);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/parking-area/{id}", invalidParkingAreaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verify
        Mockito.verify(parkingAreaDeleteService, times(1))
                .deleteParkingAreaById(invalidParkingAreaId);

    }

    @Test
     void givenValidParkingAreaId_whenUserUnauthorized_thenReturnForbidden() throws Exception {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        // When
        Mockito.doNothing().when(parkingAreaDeleteService)
                .deleteParkingAreaById(mockParkingAreaId);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/parking-area/{id}", mockParkingAreaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Verify
        Mockito.verify(parkingAreaDeleteService, never())
                .deleteParkingAreaById(mockParkingAreaId);

    }

    @Test
    void givenValidParkingAreaUpdateRequest_whenParkingAreaUpdated_thenReturnCustomResponse() throws Exception{

        //Given

        final String mockUpdateEntityId = UUID.randomUUID().toString();

        final ParkingAreaUpdateRequest mockParkingAreaUpdateRequest = new ParkingAreaUpdateRequestBuilder()
                .withValidField()
                .build();

        final ParkingAreaEntity mockParkingAreaEntity = new ParkingAreaEntityBuilder()
                .withValidFields()
                .build();

        mockParkingAreaEntity.setId(mockUpdateEntityId);
        mockParkingAreaEntity.setCapacity(mockParkingAreaUpdateRequest.getCapacity());


        final ParkingArea mockParkingAreaDomainModel = parkingAreaEntityToParkingAreaDomainModelMapper
                .map(mockParkingAreaEntity);

        //when
        Mockito.when(
                parkingAreaUpdateService.parkingAreaUpdateByCapacity(
                        mockUpdateEntityId,
                        mockParkingAreaUpdateRequest
                        )
        ).thenReturn(mockParkingAreaDomainModel);


        //then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("api/v1/parking-area/{id}",mockUpdateEntityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockParkingAreaUpdateRequest))
                        .header(HttpHeaders.AUTHORIZATION,mockAdminToken)
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(mockParkingAreaDomainModel.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("httpStatus").value("ok"));

        //verify
        Mockito.verify(parkingAreaUpdateService,Mockito.times(1))
                .parkingAreaUpdateByCapacity(Mockito.anyString(),Mockito.any(ParkingAreaUpdateRequest.class));
    }

    @Test
    void givenInvalidParkingAreaUpdateRequest_whenParkingAreaCapacityIsNull_thenReturnBadRequest() throws Exception {

        //Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaUpdateRequest mockParkingAreaUpdateRequest = new ParkingAreaUpdateRequestBuilder()
                .withValidField()
                .withCapacity(null)
                .build();

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .id(mockParkingAreaId)
                .capacity(mockParkingAreaUpdateRequest.getCapacity())
                .build();

        //when
        Mockito.when(
                parkingAreaUpdateService.parkingAreaUpdateByCapacity(
                        mockParkingAreaId,
                        mockParkingAreaUpdateRequest
                )
        ).thenThrow(new RuntimeException());


        //then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("api/v1/parking-area/{id}",mockParkingAreaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockParkingAreaUpdateRequest))
                                .header(HttpHeaders.AUTHORIZATION,mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());



        //verify
        Mockito.verify(parkingAreaUpdateService,Mockito.times(1))
                .parkingAreaUpdateByCapacity(mockParkingAreaId,mockParkingAreaUpdateRequest);

    }

    @Test
    void givenValidParkingAreaUpdateRequest_whenUserUnauthorized_thenReturnForbidden() throws Exception{

        //Given
        final ParkingAreaUpdateRequest mockUpdateRequest = new ParkingAreaUpdateRequestBuilder()
                .withValidField()
                .build();

        final String mockParkingAreaId = UUID.randomUUID().toString();

        //when
        Mockito.doNothing().when(parkingAreaUpdateService.parkingAreaUpdateByCapacity(mockParkingAreaId,mockUpdateRequest));

        //then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("api/v1/parking-area/{id}",mockParkingAreaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,mockUserToken)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        //Verify
        Mockito.verify(parkingAreaUpdateService,Mockito.times(0))
                .parkingAreaUpdateByCapacity(mockParkingAreaId,mockUpdateRequest);
    }





}

