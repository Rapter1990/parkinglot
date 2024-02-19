package com.project.parkinglot.exception;

import com.project.parkinglot.base.BaseControllerTest;
import com.project.parkinglot.exception.park.ParkNotFoundException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaAlreadyExistException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaCapacityCanNotBeNull;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.exception.pricelist.PriceListNotFoundException;
import com.project.parkinglot.exception.user.EmailAlreadyExistsException;
import com.project.parkinglot.exception.user.RefreshTokenNotFoundException;
import com.project.parkinglot.exception.user.UserNotFoundException;
import com.project.parkinglot.payload.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;

@Slf4j
class GlobalExceptionHandlerTest extends BaseControllerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void givenUnsupportedMediaType_whenThrowHttpMediaTypeNotSupportedException_thenReturnErrorResponse() {

        // Given
        HttpMediaTypeNotSupportedException mockException = new HttpMediaTypeNotSupportedException("Unsupported media type");

        // When
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(mockException.getContentType() + "Unsupported media type is not supported. Supported media types are "
                + mockException.getSupportedMediaTypes().stream().map(MediaType::toString).collect(Collectors.joining(", ")));

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(errorDetails)
                .message("Invalid JSON")
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleHttpMediaTypeNotSupported(
                mockException, mockException.getHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, null);

        Assertions.assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                ((ErrorResponse) responseEntity.getBody()).getTimestamp().toLocalDate());

    }


    @Test
    void givenMalformedJsonRequest_whenThrowHttpMessageNotReadableException_thenReturnErrorResponse() {

        // Given
        HttpInputMessage httpInputMessage = mock(HttpInputMessage.class);
        HttpMessageNotReadableException mockException = new HttpMessageNotReadableException("Malformed JSON request", httpInputMessage);

        // When
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(mockException.getMessage());

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(errorDetails)
                .message("Malformed JSON request")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleHttpMessageNotReadable(
                mockException, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                ((ErrorResponse) responseEntity.getBody()).getTimestamp().toLocalDate());
    }

    @Test
    void givenValidationErrors_whenThrowMethodArgumentNotValidException_thenReturnErrorResponse() throws NoSuchMethodException {

        // Given
        Method method = GlobalExceptionHandlerTest.class.getDeclaredMethod("givenValidationErrors_whenThrowMethodArgumentNotValidException_thenReturnErrorResponse");
        int parameterIndex = -1;

        MethodParameter mockParameter = new MethodParameter(method, parameterIndex);
        BindingResult mockBindingResult = new BeanPropertyBindingResult(null, null);
        MethodArgumentNotValidException mockException = new MethodArgumentNotValidException(mockParameter, mockBindingResult);

        // When
        List<String> details = mockException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .toList();

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Validation Errors")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleMethodArgumentNotValid(
                mockException, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                ((ErrorResponse) responseEntity.getBody()).getTimestamp().toLocalDate());

    }

    @Test
    void givenMissingServletRequestParameter_whenThrowMissingServletRequestParameterException_thenReturnErrorResponse() {

        // Given
        MissingServletRequestParameterException mockException = new MissingServletRequestParameterException(
                "parameterName", "parameterType");

        // When
        List<String> details = new ArrayList<>();
        details.add(mockException.getParameterName() + " parameter is missing");

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Validation Errors")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleMissingServletRequestParameter(
                mockException, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                ((ErrorResponse) responseEntity.getBody()).getTimestamp().toLocalDate());

    }

    @Test
    void givenParkingAreaNotFoundException_whenThrowParkingAreaNotFoundException_thenReturnErrorResponse() {

        // Given
        ParkingAreaNotFoundException mockException = new ParkingAreaNotFoundException("Parking area not found");

        // When
        List<String> details = new ArrayList<>();
        details.add(mockException.getMessage());

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Parking Area Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleParkingAreaNotFoundException(mockException);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                responseEntity.getBody().getTimestamp().toLocalDate());

    }

    @Test
    void givenParkNotFoundException_whenThrowParkNotFoundException_thenReturnErrorResponse() {

        // Given
        ParkNotFoundException mockException = new ParkNotFoundException("Park not found");

        // When
        List<String> details = new ArrayList<>();
        details.add(mockException.getMessage());

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Park Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleParkNotFoundException(mockException);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                responseEntity.getBody().getTimestamp().toLocalDate());

    }

    @Test
    void givenPriceListNotFoundException_whenThrowPriceListNotFoundException_thenReturnErrorResponse() {

        // Given
        PriceListNotFoundException mockException = new PriceListNotFoundException("Price list not found");

        // When
        List<String> details = new ArrayList<>();
        details.add(mockException.getMessage());

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Price List Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handlePriceListNotFoundException(mockException);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                responseEntity.getBody().getTimestamp().toLocalDate());

    }

    @Test
    void givenRefreshTokenNotFoundException_whenThrowRefreshTokenNotFoundException_thenReturnErrorResponse() {

        // Given
        RefreshTokenNotFoundException mockException = new RefreshTokenNotFoundException("Refresh token not found");

        // When
        List<String> details = new ArrayList<>();
        details.add(mockException.getMessage());

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Refresh Token Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleRefreshTokenNotFoundException(mockException);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                responseEntity.getBody().getTimestamp().toLocalDate());

    }

    @Test
    void givenUserNotFoundException_whenThrowUserNotFoundException_thenReturnErrorResponse() {

        // Given
        UserNotFoundException mockException = new UserNotFoundException("User not found");

        // When
        List<String> details = new ArrayList<>();
        details.add(mockException.getMessage());

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("User Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleUserNotFoundException(mockException);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                responseEntity.getBody().getTimestamp().toLocalDate());

    }

    @Test
    void givenEmailAlreadyExistsException_whenThrowEmailAlreadyExistsException_thenReturnErrorResponse() {

        // Given
        EmailAlreadyExistsException mockException = new EmailAlreadyExistsException("Email already exists");

        // When
        List<String> details = new ArrayList<>();
        details.add(mockException.getMessage());

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Email Already Exists")
                .statusCode(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleEmailAlreadyExistsException(mockException);

        Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                responseEntity.getBody().getTimestamp().toLocalDate());

    }

    @Test
    void givenParkingAreAlreadyExistException_whenThrowParkingAreAlreadyExistException_thenReturnErrorResponse() {

        // Given
        ParkingAreaAlreadyExistException mockException = new ParkingAreaAlreadyExistException("The Parking Area Name and Location already exist!");

        // When
        List<String> details = new ArrayList<>();
        details.add(mockException.getMessage());

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("The Parking Area Name and Location already exist!")
                .statusCode(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();

        // Then
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleParkingAreAlreadyExistException(mockException);

        Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                responseEntity.getBody().getTimestamp().toLocalDate());

    }

    @Test
    void givenParkingAreaCapacityCanNotBeNullException_whenThrowParkingAreaCapacityCanNotBeNullException_thenReturnErrorResponse() {

        //Given
        ParkingAreaCapacityCanNotBeNull mockException = new ParkingAreaCapacityCanNotBeNull("ParkingArea Capacity Field can not be null");

        //When
        List<String> details = new ArrayList<>();
        details.add(mockException.getMessage());

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("ParkingArea Capacity Field can not be null")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();

        //Then
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleParkingAreaNotFoundException(mockException);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
        Assertions.assertEquals(expectedErrorResponse.getTimestamp().toLocalDate(),
                responseEntity.getBody().getTimestamp().toLocalDate());

    }

}
