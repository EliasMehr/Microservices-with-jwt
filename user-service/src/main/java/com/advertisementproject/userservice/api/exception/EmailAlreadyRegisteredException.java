package com.advertisementproject.userservice.api.exception;

/**
 * Custom RuntimeException for when an email is already registered in the database
 */
public class EmailAlreadyRegisteredException extends RuntimeException {

    /**
     * Constructor
     *
     * @param message the error message to be shown
     */
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
