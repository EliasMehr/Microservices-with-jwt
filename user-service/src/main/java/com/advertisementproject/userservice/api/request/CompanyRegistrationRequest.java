package com.advertisementproject.userservice.api.request;

import com.advertisementproject.userservice.db.entity.types.CompanyType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Request object with information required to register a company user
 */
@Data
public class CompanyRegistrationRequest {

    /**
     * The name of the company to be registered.
     */
    @NotNull
    private final String name;

    /**
     * The organization number for the company to be registered.
     */
    @NotNull
    private final String organizationNumber;

    /**
     * The email address for the company to be registered.
     */
    @NotNull
    private final String email;

    /**
     * The account password for the company to be registered.
     */
    @NotNull
    private final String password;

    /**
     * Optional description of the company to be registered.
     */
    private final String description = null;

    /**
     * The address of the company to be registered.
     */
    @NotNull
    private final String address;

    /**
     * The city of the company to be registered.
     */
    @NotNull
    private final String city;

    /**
     * The zip code of the company to be registered.
     */
    @NotNull
    private final String zipCode;

    /**
     * The phone number to the company to be registered.
     */
    @NotNull
    private final String phoneNumber;

    /**
     * The company type of the company to be registered.
     */
    @NotNull
    private final CompanyType companyType;
}
