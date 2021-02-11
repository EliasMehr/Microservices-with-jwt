package com.advertisementproject.permissionservice.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * Response object for reporting exceptions in a nicer format, containing information about the error that occurred
 */
@AllArgsConstructor
@Getter
@Builder
@ToString
public class ApiError {

    /**
     * The error status of the exception
     */
    private final HttpStatus status;

    /**
     * The error message for the exception
     */
    private final String message;

    /**
     * Timestamp for when the exception was thrown
     */
    private final Instant timestamp;
}
