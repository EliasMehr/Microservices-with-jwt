package com.advertisementproject.zuulgateway.api.response;

import com.advertisementproject.zuulgateway.db.entity.types.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Response after a successful login, including the user's role and a jwt token that should be attached as a bearer
 * token in the header for any request which requires authorization.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthenticationResponse implements Serializable {
    private String token;
    private Role role;
}
