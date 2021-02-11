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

    /**
     * Error status code for the exception.
     */
    private final int statusCode;

    /**
     * Timestamp for when the error occurred.
     */
    private final String timestamp;

    /**
     * The error message for the exception.
     */
    private final String message;

}
