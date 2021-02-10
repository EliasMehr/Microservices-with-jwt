package com.advertisementproject.campaignservice.exception;

/**
 * Custom RuntimeException for an unauthorized access attempt
 */
public class UnauthorizedAccessException extends RuntimeException {

    /**
     * Constructor
     *
     * @param message the error message to be shown
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
