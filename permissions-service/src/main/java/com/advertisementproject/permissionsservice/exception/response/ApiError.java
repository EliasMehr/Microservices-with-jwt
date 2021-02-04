package com.advertisementproject.permissionsservice.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class ApiError {

    private final HttpStatus status;
    private final String message;
    private final Instant timestamp;
}
