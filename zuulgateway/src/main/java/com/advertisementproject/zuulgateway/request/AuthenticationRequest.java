package com.advertisementproject.zuulgateway.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    private final String username;
    private final String password;
}
