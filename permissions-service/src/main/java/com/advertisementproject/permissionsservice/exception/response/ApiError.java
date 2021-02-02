package com.advertisementproject.permissionsservice.exception.response;

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

    private final HttpStatus status;
    private final String message;
    private final Instant timestamp;
}
