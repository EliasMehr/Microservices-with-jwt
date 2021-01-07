package com.advertisementproject.zuulgateway.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MeResponse {

    private String userId;
    private String username;
    private String password;



}
