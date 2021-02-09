package com.advertisementproject.permissionsservice.exception.handler;

import com.advertisementproject.permissionsservice.exception.PermissionsNotFoundException;
import com.advertisementproject.permissionsservice.exception.response.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
     * Handles exception reporting for PermissionsNotFoundException with status 404 Not Found
     *
     * @param ex the exception that was thrown
     * @return response entity with an ApiError response object
     */
    @ExceptionHandler({PermissionsNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFoundException(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.NOT_FOUND);
    }


    /**
     * Helper method to log the exception and return an ApiError response object
     *
     * @param ex         the exception that was thrown
     * @param httpStatus the error status for the exception
     * @return response entity with an ApiError response object
     */
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
