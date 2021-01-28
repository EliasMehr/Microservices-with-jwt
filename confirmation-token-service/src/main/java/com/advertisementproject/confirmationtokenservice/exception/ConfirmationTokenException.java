package com.advertisementproject.confirmationtokenservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConfirmationTokenException extends RuntimeException{

    private final HttpStatus httpStatus;

    public ConfirmationTokenException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
