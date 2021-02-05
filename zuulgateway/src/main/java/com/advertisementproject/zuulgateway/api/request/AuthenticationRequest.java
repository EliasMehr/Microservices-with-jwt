package com.advertisementproject.zuulgateway.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Request for logging in as a user including username (email) and password
 */
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationRequest implements Serializable {
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
