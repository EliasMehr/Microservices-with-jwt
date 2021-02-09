package com.advertisementproject.zuulgateway.api.exceptions;

/**
 * Runtime exception for when an entity is not found
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructor
     *
     * @param message the error message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
