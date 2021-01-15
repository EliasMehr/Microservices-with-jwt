package com.advertisementproject.userservice.api.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
