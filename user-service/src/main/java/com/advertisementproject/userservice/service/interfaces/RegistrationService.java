package com.advertisementproject.userservice.service.interfaces;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.messagebroker.dto.EmailDetailsMessage;

import javax.transaction.Transactional;

/**
 * Registration Service that registers customer users or company users, as well as retrieves email details. This service
 * enables registration controller to function.
 */
public interface RegistrationService {

    /**
     * Registers a customer user using the information supplied in the registration request.
     * @param registrationRequest request object with all information needed to register a customer user.
     * @return the newly registered customer user
     */
    @Transactional
    CustomerUserResponse registerCustomer(CustomerRegistrationRequest registrationRequest);

    /**
     * Registers a company user using the information supplied in the registration request.
     * @param registrationRequest request object with all information needed to register a company user.
     * @return the newly registered company user
     */
    @Transactional
    CompanyUserResponse registerCompany(CompanyRegistrationRequest registrationRequest);

    /**
     * Retrieves email details for a customer/company user with the supplied email
     * @param email the email for which to retrieve email details
     * @return email details for the supplied email
     */
    EmailDetailsMessage getEmailDetails(String email);
}
