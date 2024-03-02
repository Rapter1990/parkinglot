package com.project.parkinglot.exception;

import com.project.parkinglot.exception.park.ParkNotFoundException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaCapacityCanNotBeNullException;
import com.project.parkinglot.exception.parkingarea.ParkingAreaNotFoundException;
import com.project.parkinglot.exception.pricelist.PriceListNotFoundException;
import com.project.parkinglot.exception.user.EmailAlreadyExistsException;
import com.project.parkinglot.exception.user.RefreshTokenNotFoundException;
import com.project.parkinglot.exception.user.UserNotFoundException;
import com.project.parkinglot.exception.vehicle.VehicleAlreadyExist;
import com.project.parkinglot.payload.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatusCode status,
                                                                     WebRequest request) {
        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append("Unsupported media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        details.add(builder.toString());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Invalid JSON")
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.error(ex.getMessage(), ex);


        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Malformed JSON request")
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.error(ex.getMessage(), ex);

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Validation Errors")
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);

    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          WebRequest request) {

        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();
        details.add(ex.getParameterName() + " parameter is missing");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Validation Errors")
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);

    }

    @ExceptionHandler(value = {ParkingAreaNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleParkingAreaNotFoundException(final ParkingAreaNotFoundException ex) {

        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Parking Area Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ExceptionHandler(value = {ParkNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleParkNotFoundException(final ParkNotFoundException ex) {

        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Park Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ExceptionHandler(value = {PriceListNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handlePriceListNotFoundException(final PriceListNotFoundException ex) {

        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Price List Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    protected ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(final EmailAlreadyExistsException ex) {

        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Email Already Exists")
                .statusCode(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

    }

    @ExceptionHandler(value = {RefreshTokenNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(final RefreshTokenNotFoundException ex) {

        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Refresh Token Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);


    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleUserNotFoundException(final UserNotFoundException ex) {

        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("User Not Found")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ExceptionHandler(com.project.parkinglot.exception.parkingarea.ParkingAreaAlreadyExistException.class)
    protected ResponseEntity<ErrorResponse> handleParkingAreAlreadyExistException(final com.project.parkinglot.exception.parkingarea.ParkingAreaAlreadyExistException ex) {

        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Parking area already exists")
                .statusCode(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

    }

    @ExceptionHandler(ParkingAreaCapacityCanNotBeNullException.class)
    protected ResponseEntity<ErrorResponse> handleParkingAreaNotFoundException(final ParkingAreaCapacityCanNotBeNullException ex) {

        log.error(ex.getMessage(), ex);

        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("ParkingArea capacity cannot be null")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }
    @ExceptionHandler(VehicleAlreadyExist.class)
    protected  ResponseEntity<ErrorResponse> handleUserAlreadyHasThisVehicleException(final VehicleAlreadyExist ex){

        log.error(ex.getMessage(), ex);

        final List<String> details =new ArrayList<>();
        details.add(ex.getMessage());

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Vehicle already exist")
                .statusCode(HttpStatus.CONFLICT.value())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

}
