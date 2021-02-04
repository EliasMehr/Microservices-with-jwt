package com.advertisementproject.permissionsservice.exception.handler;

import com.advertisementproject.permissionsservice.exception.PermissionsNotFoundException;
import com.advertisementproject.permissionsservice.exception.response.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class CustomRestExceptionHandler {


    @ExceptionHandler({PermissionsNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFoundException(Exception ex){
        return getAndLogApiError(ex, HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<ApiError> getAndLogApiError(Exception ex, HttpStatus httpStatus) {
        ApiError apiError = ApiError.builder()
                .status(httpStatus)
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build();

        log.warn(apiError.toString());
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
