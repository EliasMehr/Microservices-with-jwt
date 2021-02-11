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

    /**
     * The core user details for the company user.
     */
    private final User user;

    /**
     * The company information for the company user.
     */
    private final Company company;

}
