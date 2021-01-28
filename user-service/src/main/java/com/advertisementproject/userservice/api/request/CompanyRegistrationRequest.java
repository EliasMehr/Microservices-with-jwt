package com.advertisementproject.userservice.api.request;

import com.advertisementproject.userservice.db.model.types.CompanyType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyRegistrationRequest {
    @NotNull
    private final String name;
    @NotNull
    private final String organizationNumber;
    @NotNull
    private final String email;
    @NotNull
    private final String password;
    private final String description = null;
    @NotNull
    private final String address;
    @NotNull
    private final String city;
    @NotNull
    private final String zipCode;
    @NotNull
    private final String phoneNumber;
    @NotNull
    private final CompanyType companyType;
}
