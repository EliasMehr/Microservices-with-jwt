package com.advertisementproject.zuulgateway.api.exceptions;

public class PermissionsNotFoundException extends RuntimeException{

    public PermissionsNotFoundException(String message) {
        super(message);
    }
}
