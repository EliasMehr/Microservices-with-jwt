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

    private final HttpStatus status;
    private final String message;
    private final String timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<String> errors;
}
