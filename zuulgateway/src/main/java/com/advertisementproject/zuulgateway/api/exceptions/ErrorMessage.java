package com.advertisementproject.zuulgateway.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Builder
public class ErrorMessage {
    private final int statusCode;
    private final Instant timestamp;
    private final String message;
}
