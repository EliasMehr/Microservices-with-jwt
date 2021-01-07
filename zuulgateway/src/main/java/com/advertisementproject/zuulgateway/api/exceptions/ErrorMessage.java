package com.advertisementproject.zuulgateway.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ErrorMessage {
    private final int statusCode;
    private final String timestamp;
    private final String message;
}
