package com.advertisementproject.userservice.service.interfaces;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.messagebroker.dto.EmailDetailsMessage;

import javax.transaction.Transactional;

public interface RegistrationService {
    @Transactional
    CustomerUserResponse registerCustomer(CustomerRegistrationRequest registrationRequest);

    @Transactional
    CompanyUserResponse registerCompany(CompanyRegistrationRequest registrationRequest);

    EmailDetailsMessage getEmailDetails(String email);
}
