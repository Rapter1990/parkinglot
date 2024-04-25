package com.project.parkinglot.controller;

import com.project.parkinglot.base.BaseControllerTest;
import com.project.parkinglot.builder.ParkingAreaCreateRequestBuilder;
import com.project.parkinglot.builder.ParkingAreaUpdateRequestBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaCapacityCanNotBeNullException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaUpdateRequest;
import com.project.parkinglot.model.dto.response.parkingarea.ParkingAreaIncomeResponse;
import com.project.parkinglot.service.parking_area.ParkingAreaCreateService;
import com.project.parkinglot.service.parking_area.ParkingAreaDeleteService;
import com.project.parkinglot.service.parking_area.ParkingAreaGetService;
import com.project.parkinglot.service.parking_area.ParkingAreaUpdateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class ParkingAreaControllerTest extends BaseControllerTest {

    @MockBean
    private ParkingAreaCreateService parkingAreaCreateService;

    @MockBean
    private ParkingAreaGetService parkingAreaGetService;

    @MockBean
    private ParkingAreaDeleteService parkingAreaDeleteService;

    @MockBean
    private ParkingAreaUpdateService parkingAreaUpdateService;

    @Test
    void givenValidParkingAreaCreateRequest_whenParkingAreaCreated_thenReturnCustomResponse() throws Exception {

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
        Mockito.verify(parkingAreaCreateService, Mockito.times(1))
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
        Mockito.verify(parkingAreaCreateService, Mockito.never())
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }

    @Test
    void givenInvalidParkingAreaCreateRequest_whenParkingAreaCapacityLessThanZero_thenReturnBadRequest() throws Exception {

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
        Mockito.verify(parkingAreaCreateService, Mockito.never())
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }

    @Test
    void givenInvalidParkingAreaCreateRequest_whenParkingAreaCapacityIsNull_thenReturnBadRequest() throws Exception {

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
        Mockito.verify(parkingAreaCreateService, Mockito.never())
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

    }

    @Test
    void givenInvalidParkingAreaCreateRequest_whenParkingAreaNameIsNull_thenReturnBadRequest() throws Exception {

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
        Mockito.verify(parkingAreaCreateService, Mockito.never())
                .createParkingArea(Mockito.any(ParkingAreaCreateRequest.class));

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
                        .get("/api/v1/parking-area/id/{parkingAreaId}", mockParkingAreaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(mockParkingAreaId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value("Mock Parking Area"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.capacity").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.city").value("Mock City"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.location").value("Mock Location"));

        // Verify
        Mockito.verify(parkingAreaGetService, Mockito.times(1)).getParkingAreaById(mockParkingAreaId);

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
                        .get("/api/v1/parking-area/id/{parkingAreaId}", invalidParkingAreaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verify
        Mockito.verify(parkingAreaGetService, Mockito.times(1)).getParkingAreaById(invalidParkingAreaId);

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
                                .get("/api/v1/parking-area/id/{parkingAreaId}", mockParkingAreaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Verify
        Mockito.verify(parkingAreaGetService, Mockito.never()).getParkingAreaById(mockParkingAreaId);

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
        Mockito.verify(parkingAreaDeleteService, Mockito.times(1))
                .deleteParkingAreaById(mockParkingAreaId);

    }

    @Test
    void givenInvalidParkingAreaId_whenParkingAreaDeleted_thenReturnNotFound() throws Exception {

        // Given
        final String invalidParkingAreaId = UUID.randomUUID().toString();

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
        Mockito.verify(parkingAreaDeleteService, Mockito.times(1))
                .deleteParkingAreaById(invalidParkingAreaId);

    }

    @Test
    void givenValidDeleteParkingAreaById_whenUserUnauthorized_thenReturnForbidden() throws Exception {

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
        Mockito.verify(parkingAreaDeleteService, Mockito.never())
                .deleteParkingAreaById(mockParkingAreaId);

    }

    @Test
    void givenValidParkingAreaUpdateRequest_whenParkingAreaUpdated_thenReturnCustomResponse() throws Exception {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaUpdateRequest mockParkingAreaUpdateRequest = new ParkingAreaUpdateRequestBuilder()
                .withValidFields()
                .build();

        final ParkingArea mockParkingAreaDomainModel = ParkingArea
                .builder()
                .id(mockParkingAreaId)
                .capacity(mockParkingAreaUpdateRequest.getCapacity())
                .name("deneme")
                .city("istanbul")
                .location("lokasyon")
                .build();


        // When
        Mockito.when(
                parkingAreaUpdateService.parkingAreaUpdateByCapacity(
                        Mockito.anyString(),
                        Mockito.any(ParkingAreaUpdateRequest.class)
                )
        ).thenReturn(mockParkingAreaDomainModel);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/parking-area/{id}", mockParkingAreaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockParkingAreaUpdateRequest))
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .value("Parking area with id " + mockParkingAreaDomainModel.getId() + " is updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

        // Verify
        Mockito.verify(parkingAreaUpdateService, Mockito.times(1))
                .parkingAreaUpdateByCapacity(Mockito.anyString(), Mockito.any(ParkingAreaUpdateRequest.class));

    }

    @Test
    void givenInvalidParkingAreaUpdateRequest_whenParkingAreaCapacityIsNull_thenReturnBadRequest() throws Exception {

        // Given
        final String mockParkingAreaId = UUID.randomUUID().toString();

        final ParkingAreaUpdateRequest mockParkingAreaUpdateRequest = new ParkingAreaUpdateRequestBuilder()
                .withValidFields()
                .withCapacity(null)
                .build();

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .id(mockParkingAreaId)
                .capacity(mockParkingAreaUpdateRequest.getCapacity())
                .build();

        // When
        Mockito.when(
                parkingAreaUpdateService.parkingAreaUpdateByCapacity(
                        mockParkingAreaId,
                        mockParkingAreaUpdateRequest
                )
        ).thenThrow(new ParkingAreaCapacityCanNotBeNullException());

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/parking-area/{id}", mockParkingAreaId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockParkingAreaUpdateRequest))
                                .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Verify
        Mockito.verify(parkingAreaUpdateService, Mockito.times(0))
                .parkingAreaUpdateByCapacity(mockParkingAreaId, mockParkingAreaUpdateRequest);

    }

    @Test
    public void givenValidParkingAreaName_whenGetParkingAreaByName_thenReturnParkingArea() throws Exception {

        // Given
        final String mockParkingAreaName = "parking-area-name";

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .name(mockParkingAreaName)
                .capacity(100)
                .city("Mock City")
                .location("Mock Location")
                .build();

        // When
        Mockito.when(parkingAreaGetService.getParkingAreaByName(mockParkingAreaName))
                .thenReturn(mockParkingArea);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/parking-area/name/{name}", mockParkingAreaName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value(mockParkingAreaName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.capacity").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.city").value("Mock City"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.location").value("Mock Location"));

        // Verify
        Mockito.verify(parkingAreaGetService, Mockito.times(1)).getParkingAreaByName(mockParkingAreaName);

    }

    @Test
    public void givenInvalidParkingAreaName_whenGetParkingAreaByName_thenReturnNotFound() throws Exception {

        // Given
        final String mockParkingAreaName = "invalid-parking-area-name";

        // When
        Mockito.when(parkingAreaGetService.getParkingAreaByName(mockParkingAreaName))
                .thenThrow(new ParkingAreaNotFoundException("Parking area not found with name: " + mockParkingAreaName));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/parking-area/name/{name}", mockParkingAreaName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verify
        Mockito.verify(parkingAreaGetService, Mockito.times(1)).getParkingAreaByName(mockParkingAreaName);

    }

    @Test
    public void givenValidGetParkingAreaByName_whenUserUnauthorized_thenReturnForbidden() throws Exception {

        // Given
        final String mockParkingAreaName = "parking-area-name";

        final ParkingArea mockParkingArea = ParkingArea.builder()
                .name(mockParkingAreaName)
                .capacity(100)
                .city("Mock City")
                .location("Mock Location")
                .build();

        // When
        Mockito.when(parkingAreaGetService.getParkingAreaByName(mockParkingAreaName))
                .thenReturn(mockParkingArea);

        // Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/parking-area/name/{name}", mockParkingAreaName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Verify
        Mockito.verify(parkingAreaGetService, Mockito.never()).getParkingAreaByName(mockParkingAreaName);

    }


    @Test
    void givenValidRequestWithAdminRole_whenGetDailyIncome_thenReturnsIncome() throws Exception {

        // Given
        final String date = "21-04-2023";
        final String parkingAreaId = "area123";
        final ParkingAreaIncomeResponse incomeResponse = ParkingAreaIncomeResponse.builder()
                .income(new BigDecimal("1200"))
                .name("Parking name")
                .build();

        // When
        Mockito.when(parkingAreaGetService.getDailyIncome(Mockito.any(LocalDate.class), Mockito.any(String.class)))
                .thenReturn(incomeResponse);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking-area/income")
                        .param("date", date)
                        .param("parkingAreaId", parkingAreaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value("Parking name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.income").value("1200"));


        // Verify
        Mockito.verify(parkingAreaGetService, Mockito.times(1))
                .getDailyIncome(Mockito.any(LocalDate.class), Mockito.any(String.class));

    }

    @Test
    void givenValidRequestWithUserRole_whenUserRoleUnAuthorized_thenThrowsForbidden() throws Exception {

        // Given
        final String date = "21-04-2023";
        final String parkingAreaId = "area123";
        final ParkingAreaIncomeResponse incomeResponse = ParkingAreaIncomeResponse.builder()
                .income(new BigDecimal("1200"))
                .name("Parking name")
                .build();

        // When
        Mockito.when(parkingAreaGetService.getDailyIncome(Mockito.any(LocalDate.class), Mockito.any(String.class)))
                .thenReturn(incomeResponse);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking-area/income")
                        .param("date", date)
                        .param("parkingAreaId", parkingAreaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Verify
        Mockito.verify(parkingAreaGetService, Mockito.never())
                .getDailyIncome(Mockito.any(LocalDate.class), Mockito.any(String.class));

    }

}
