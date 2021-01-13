package com.advertisementproject.userservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class ApiError {

    private final HttpStatus status;
    private final String message;
    private final String timestamp;
    private final List<String> errors;
}
