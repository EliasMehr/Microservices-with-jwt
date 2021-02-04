package com.advertisementproject.zuulgateway.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Error report object for showing exceptions to the client
 */
@AllArgsConstructor
@Getter
@Builder
@ToString
public class ApiError {

    private final int statusCode;
    private final String timestamp;
    private final String message;

}
