package com.advertisementproject.confirmationtokenservice.exception.handler;

import com.advertisementproject.confirmationtokenservice.exception.ConfirmationTokenException;
import com.advertisementproject.confirmationtokenservice.exception.response.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class CustomRestExceptionHandler {

    @ExceptionHandler({ConfirmationTokenException.class})
    public ResponseEntity<ApiError> handleConfirmationTokenException(ConfirmationTokenException ex){
        return getAndLogApiError(ex);
    }

    private ResponseEntity<ApiError> getAndLogApiError(ConfirmationTokenException ex) {
        ApiError apiError = ApiError.builder()
                .status(ex.getHttpStatus())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build();

        log.warn(apiError.toString());
        return new ResponseEntity<>(apiError, ex.getHttpStatus());
    }
}
