package com.advertisementproject.userservice.api.response;

import com.advertisementproject.userservice.db.models.Company;
import com.advertisementproject.userservice.db.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CompanyUserResponse {

    private final User user;
    private final Company company;

}
