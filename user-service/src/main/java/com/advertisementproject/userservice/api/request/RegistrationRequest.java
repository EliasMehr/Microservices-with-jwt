package com.advertisementproject.userservice.api.request;

import com.advertisementproject.userservice.db.models.types.CompanyType;
import lombok.*;
import javax.validation.constraints.NotNull;

@Data
public class RegistrationRequest {
    @NotNull
    private final String identificationNumber;
    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    @NotNull
    private final String address;
    @NotNull
    private final String city;
    @NotNull
    private final String zipCode;
    @NotNull
    private final String phoneNumber;
    @NotNull
    private final String email;
    @NotNull
    private final String password;

    private final CompanyType type = CompanyType.NOT_SPECIFIED;
}
