package com.advertisementproject.zuulgateway.api.response;

import com.advertisementproject.zuulgateway.db.models.types.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
