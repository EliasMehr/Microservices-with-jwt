package com.advertisementproject.zuulgateway.response;

public class AuthenticationResponse {
     String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
