package com.advertisementproject.confirmationtokenservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom RuntimeException for exceptions related to confirmation tokens. Includes an http status and an error message.
 */
@Getter
public class ConfirmationTokenException extends RuntimeException {

    /**
     * Error status for the exception
     */
    private final HttpStatus httpStatus;

    /**
     * Constructor
     *
     * @param message    the message for the exception
     * @param httpStatus the error status for the exception
     */
    public ConfirmationTokenException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
