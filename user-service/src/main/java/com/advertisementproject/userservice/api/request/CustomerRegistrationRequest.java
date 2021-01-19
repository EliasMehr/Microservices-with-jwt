package com.advertisementproject.userservice.api.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerRegistrationRequest {
    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    @NotNull
    private final String personalIdNumber;
    @NotNull
    private final String email;
    @NotNull
    private final String password;
    @NotNull
    private final String address;
    @NotNull
    private final String city;
    @NotNull
    private final String zipCode;
    @NotNull
    private final String phoneNumber;
}
