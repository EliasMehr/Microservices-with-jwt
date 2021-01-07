package com.advertisementproject.zuulgateway.api.request;

import lombok.Data;

@Data
public class RegistrationRequest {

    private final String identificationNumber;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String address;
    private final String city;
    private final int zipCode;
    private final String email;
    private final String password;
    private final String type;

}
