package com.advertisementproject.confirmationtokenservice.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * Api Error report object that shows an error report to the client.
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
