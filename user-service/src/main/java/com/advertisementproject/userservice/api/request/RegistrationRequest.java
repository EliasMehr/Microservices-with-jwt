package com.advertisementproject.userservice.api.request;

import com.advertisementproject.userservice.db.models.types.CompanyType;
import lombok.Data;

import static com.advertisementproject.userservice.db.models.types.CompanyType.NOT_SPECIFIED;

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
    private final CompanyType type = NOT_SPECIFIED;
}
