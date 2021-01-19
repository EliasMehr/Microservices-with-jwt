package com.advertisementproject.zuulgateway.api.request;

import lombok.Data;

import static com.advertisementproject.zuulgateway.db.models.types.CompanyType.NOT_SPECIFIED;

@Data
public class RegistrationRequest {

    private final String identificationNumber;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String address;
    private final String city;
    private final String zipCode;
    private final String email;
    private final String password;
    private final CompanyType type = NOT_SPECIFIED;

}
