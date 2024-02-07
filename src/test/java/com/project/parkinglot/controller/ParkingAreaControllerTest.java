package com.project.parkinglot.controller;

import com.project.parkinglot.base.BaseControllerTest;

import com.project.parkinglot.builder.ParkingAreaCreateRequestBuilder;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaCreateRequestToParkingAreaEntityMapper;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaDomainModelMapper;
import com.project.parkinglot.service.parking_area.impl.ParkingAreaCreateServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;


public class ParkingAreaControllerTest extends BaseControllerTest {

    @MockBean
    private ParkingAreaCreateServiceImpl parkingAreaCreateService;

    private final ParkingAreaCreateRequestToParkingAreaEntityMapper parkingAreaCreateRequestToParkingAreaEntityMapper =
            ParkingAreaCreateRequestToParkingAreaEntityMapper.initialize();

    private final ParkingAreaEntityToParkingAreaDomainModelMapper parkingAreaEntityToParkingAreaDomainModelMapper =
            ParkingAreaEntityToParkingAreaDomainModelMapper.initialize();

    @Test
    public void givenValidParkingAreaCreateRequest_whenParkingAreaCreated_thenReturnCustomResponse() throws Exception {

        // Given
        String mockParkingAreaId = UUID.randomUUID().toString();

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
        Mockito.verify(parkingAreaCreateService, Mockito.times(1))
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }

    @Test
    public void givenValidParkingAreaCreateRequest_whenUserUnauthorized_thenReturnForbidden() throws Exception {

        // Given
        final ParkingAreaCreateRequest mockParkingAreaCreateRequest = new ParkingAreaCreateRequestBuilder()
                .withValidFields().build();

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
        Mockito.verify(parkingAreaCreateService, Mockito.never())
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
        Mockito.verify(parkingAreaCreateService, Mockito.never())
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
        Mockito.verify(parkingAreaCreateService, Mockito.never())
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
        Mockito.verify(parkingAreaCreateService, Mockito.never())
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }

}
