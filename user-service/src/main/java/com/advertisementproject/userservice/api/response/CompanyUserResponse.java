package com.advertisementproject.userservice.api.response;

import com.advertisementproject.userservice.db.entity.Company;
import com.advertisementproject.userservice.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response object including a user and company, in other words a company user
 */
@AllArgsConstructor
@Getter
public class CompanyUserResponse {

    private final User user;
    private final Company company;

}
