package com.advertisementproject.zuulgateway.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ResponseException extends AuthenticationException {


    public ResponseException(String message) {
        super(message);
    }

}
