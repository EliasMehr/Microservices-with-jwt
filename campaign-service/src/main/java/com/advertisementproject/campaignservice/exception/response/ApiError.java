package com.advertisementproject.campaignservice.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Api Error report object that shows an error report to the client. Only includes errors in JSON if not null.
 */
@AllArgsConstructor
@Getter
@Builder
@ToString
public class ApiError {

    /**
     * The error status of the exception.
     */
    private final HttpStatus status;

    /**
     * The error message for the exception.
     */
    private final String message;

    /**
     * Timestamp for when the exception occurred.
     */
    private final String timestamp;

    /**
     * List of sub-errors such as field errors. Only shown in JSON if not null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<String> errors;
}
