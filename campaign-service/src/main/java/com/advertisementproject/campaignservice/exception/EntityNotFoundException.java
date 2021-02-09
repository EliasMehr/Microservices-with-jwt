package com.advertisementproject.campaignservice.exception;

/**
 * Custom RuntimeException for when an entity is not found in the database
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
