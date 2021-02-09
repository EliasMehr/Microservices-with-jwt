package com.advertisementproject.permissionservice.exception;

/**
 * Custom RuntimeException for when the requested permission is not found in the database
 */
public class PermissionNotFoundException extends RuntimeException {
    /**
     * Constructor
     *
     * @param message the message to be shown for the exception
     */
    public PermissionNotFoundException(String message) {
        super(message);
    }

}