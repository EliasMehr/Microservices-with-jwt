package com.advertisementproject.userservice.api.request;

import com.advertisementproject.userservice.db.entity.types.CompanyType;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String name;
    private String firstName;
    private String lastName;
    private String organizationNumber;
    private String personalIdNumber;
    private String email;
    private String password;
    private String description;
    private String address;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private CompanyType companyType;

}
