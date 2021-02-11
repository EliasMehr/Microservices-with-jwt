package com.advertisementproject.userservice.api.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Request object with information required to register a customer user
 */
@Data
public class CustomerRegistrationRequest {

    /**
     * The first name of the customer to be registered.
     */
    @NotNull
    private final String firstName;

    /**
     * The last name of the customer to be registered.
     */
    @NotNull
    private final String lastName;

    /**
     * The personal id of the customer to be registered.
     */
    @NotNull
    private final String personalIdNumber;

    /**
     * The email address for the customer to be registered.
     */
    @NotNull
    private final String email;

    /**
     * The account password for the customer to be registered.
     */
    @NotNull
    private final String password;

    /**
     * The address of the customer to be registered.
     */
    @NotNull
    private final String address;

    /**
     * The city of the customer to be registered.
     */
    @NotNull
    private final String city;

    /**
     * The zip code of the customer to be registered.
     */
    @NotNull
    private final String zipCode;

    /**
     * The phone number to the customer to be registered.
     */
    @NotNull
    private final String phoneNumber;
}
