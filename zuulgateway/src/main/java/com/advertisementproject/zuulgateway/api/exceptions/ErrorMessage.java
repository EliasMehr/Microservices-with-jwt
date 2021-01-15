package com.advertisementproject.zuulgateway.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class ErrorMessage {

    private final int statusCode;
    private final String timestamp;
    private final String message;

}
