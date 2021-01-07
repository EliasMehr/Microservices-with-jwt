package com.advertisementproject.zuulgateway.api.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    @NotEmpty(message = "Username cannot be empty")
    private final String username;

    @NotEmpty
    @Size(min = 6, max = 12, message = "Password must be 6 to 12 characters long")
    private final String password;
}
