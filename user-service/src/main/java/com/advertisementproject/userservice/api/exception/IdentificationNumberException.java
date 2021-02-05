package com.advertisementproject.userservice.api.exception;

/**
 * Custom IllegalArgumentException thrown when a personal id number or organization number has invalid format
 */
public class IdentificationNumberException extends IllegalArgumentException{

    /**
     * Constructor
     * @param s the exception message
     */
    public IdentificationNumberException(String s) {
        super(s);
    }
}
