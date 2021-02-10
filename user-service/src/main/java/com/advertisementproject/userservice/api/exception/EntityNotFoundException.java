package com.advertisementproject.userservice.api.exception;

/**
 * Custom RuntimeException for when a user is not found in the database
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructor
     *
     * @param message the error message to be shown
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
