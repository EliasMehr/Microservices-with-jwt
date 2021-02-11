package com.advertisementproject.userservice.api.request;

import com.advertisementproject.userservice.db.entity.types.CompanyType;
import lombok.Data;

/**
 * Request object with fields to update a customer user or company user. Only the desired fields to be updated needs to
 * be supplied in the request since null checks are made for all relevant fields.
 */
@Data
public class UpdateUserRequest {

    /**
     * The name of the company user to update.
     */
    private String name;

    /**
     * The first name of the customer user to update.
     */
    private String firstName;

    /**
     * The last name of the customer user to update.
     */
    private String lastName;

    /**
     * The organization number of the company user to update.
     */
    private String organizationNumber;

    /**
     * The personal id of the customer user to update.
     */
    private String personalIdNumber;

    /**
     * The email for the customer/company user to update.
     */
    private String email;

    /**
     * The password for the customer/company user to update.
     */
    private String password;

    /**
     * The description for the company user to update.
     */
    private String description;

    /**
     * The address of the customer/company user to update.
     */
    private String address;

    /**
     * The city of the customer/company user to update.
     */
    private String city;

    /**
     * The zip code of the customer/company user to update.
     */
    private String zipCode;

    /**
     * The phone number to the customer/company user to update.
     */
    private String phoneNumber;

    /**
     * The company type for company user to update.
     */
    private CompanyType companyType;
}
