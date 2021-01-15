package com.advertisementproject.zuulgateway.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RegistrationException extends RuntimeException {

    public RegistrationException(String message) {
        super(message);
    }

}
