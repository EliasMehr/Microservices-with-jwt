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

    /**
     * Username (email) supplied by a user that wants to log in to the system.
     */
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    /**
     * Password supplied by a user that wants to log in to the system.
     */
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
