package com.advertisementproject.userservice.api.exception;

public class PermissionsNotFoundException extends RuntimeException{

    public PermissionsNotFoundException(String message) {
        super(message);
    }
}
