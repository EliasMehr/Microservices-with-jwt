package com.advertisementproject.zuulgateway.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthenticationResponse implements Serializable {
    private  String token;
}
