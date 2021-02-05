package com.advertisementproject.permissionsservice.exception;

/**
 * Custom RuntimeException for when the requested permissions are not found in the database
 */
public class PermissionsNotFoundException extends RuntimeException{
    /**
     * Constructor
     * @param message the message to be shown for the exception
     */
    public PermissionsNotFoundException(String message) {
        super(message);
    }

}