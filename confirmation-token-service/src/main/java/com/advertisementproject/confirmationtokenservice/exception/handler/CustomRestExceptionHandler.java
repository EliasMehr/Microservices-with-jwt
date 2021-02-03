package com.advertisementproject.confirmationtokenservice.exception.handler;

import com.advertisementproject.confirmationtokenservice.exception.ConfirmationTokenException;
import com.advertisementproject.confirmationtokenservice.exception.response.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Exception handler to provide more clear information to the api user when exceptions occur. Exceptions thrown during
 * a controller request will return an ApiError response object in a response entity.
 */
@Slf4j
@RestControllerAdvice
public class CustomRestExceptionHandler {

    /**
     * Handles confirmation token related exceptions
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
    @ExceptionHandler({ConfirmationTokenException.class})
    public ResponseEntity<ApiError> handleConfirmationTokenException(ConfirmationTokenException ex){
        return getAndLogApiError(ex);
    }

    /**
     * Helper method for logging an error and generating a response entity with an ApiError report
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
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
