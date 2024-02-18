package com.project.parkinglot.controller;

import com.project.parkinglot.base.BaseControllerTest;
import com.project.parkinglot.builder.ParkingAreaCreateRequestBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.service.parking_area.impl.ParkingAreaCreateServiceImpl;
import com.project.parkinglot.service.parking_area.impl.ParkingAreaDeleteServiceImpl;
import com.project.parkinglot.service.parking_area.impl.ParkingAreaGetServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.*;


public class ParkingAreaControllerTest extends BaseControllerTest {

    @MockBean
    private ParkingAreaCreateServiceImpl parkingAreaCreateService;

    @MockBean
    private ParkingAreaGetServiceImpl parkingAreaGetService;

    @MockBean
    private ParkingAreaDeleteServiceImpl parkingAreaDeleteService;

    @Test
    public void givenValidParkingAreaCreateRequest_whenParkingAreaCreated_thenReturnCustomResponse() throws Exception {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaCreateRequest mockParkingAreaCreateRequest = new ParkingAreaCreateRequestBuilder()
                .withValidFields()
                .build();

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .id(mockParkingAreaId)
                .name(mockParkingAreaCreateRequest.getName())
                .capacity(mockParkingAreaCreateRequest.getCapacity())
                .city(mockParkingAreaCreateRequest.getCity())
                .location(mockParkingAreaCreateRequest.getLocation())
                .build();

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
    public void givenValidParkingAreaCreateRequest_whenUserUnauthorized_thenReturnForbidden() throws Exception {

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

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .capacity(mockParkingAreaCreateRequest.getCapacity())
                .build();

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

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .capacity(mockParkingAreaCreateRequest.getCapacity())
                .build();

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

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .name(mockParkingAreaCreateRequest.getName())
                .build();

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
    public void givenValidParkingAreaId_whenParkingAreaDeleted_thenReturnCustomResponse() throws Exception {

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
    public void givenInvalidParkingAreaId_whenParkingAreaDeleted_thenReturnNotFound() throws Exception {

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
    public void givenValidDeleteParkingAreaById_whenUserUnauthorized_thenReturnForbidden() throws Exception {

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
    public void givenValidParkingAreaId_whenGetParkingAreaById_thenReturnParkingArea() throws Exception {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .id(mockParkingAreaId)
                .name("Mock Parking Area")
                .capacity(100)
                .city("Mock City")
                .location("Mock Location")
                .build();

        // When
        Mockito.when(parkingAreaGetService.getParkingAreaById(Mockito.anyString()))
                .thenReturn(mockParkingArea);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/parking-area/{parkingAreaId}", mockParkingAreaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(mockParkingAreaId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value("Mock Parking Area"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.capacity").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.city").value("Mock City"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.location").value("Mock Location"));

        // Verify
        Mockito.verify(parkingAreaGetService, times(1)).getParkingAreaById(mockParkingAreaId);

    }

    @Test
    public void givenInvalidParkingAreaId_whenGetParkingAreaById_thenReturnNotFound() throws Exception {

        // Given
        final String invalidParkingAreaId = UUID.randomUUID().toString();

        // When
        Mockito.when(parkingAreaGetService.getParkingAreaById(Mockito.anyString()))
                .thenThrow(new ParkingAreaNotFoundException("Parking area not found with id: " + invalidParkingAreaId));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/parking-area/{parkingAreaId}", invalidParkingAreaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verify
        Mockito.verify(parkingAreaGetService, times(1)).getParkingAreaById(invalidParkingAreaId);

    }

    @Test
    public void givenValidGetParkingAreaById_whenUserUnauthorized_thenReturnForbidden() throws Exception {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .id(mockParkingAreaId)
                .name("Mock Parking Area")
                .capacity(100)
                .city("Mock City")
                .location("Mock Location")
                .build();

        // When
        Mockito.when(parkingAreaGetService.getParkingAreaById(Mockito.anyString()))
                .thenReturn(mockParkingArea);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/v1/parking-area/{parkingAreaId}", mockParkingAreaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Verify
        Mockito.verify(parkingAreaGetService, never()).getParkingAreaById(mockParkingAreaId);

    }

}
