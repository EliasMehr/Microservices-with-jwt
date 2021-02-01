package com.advertisementproject.zuulgateway.api.response;

import com.advertisementproject.zuulgateway.db.model.types.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthenticationResponse implements Serializable {
    private String token;
    private Role role;
}
