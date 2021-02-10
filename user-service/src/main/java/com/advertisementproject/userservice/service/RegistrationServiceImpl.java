package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.db.entity.Company;
import com.advertisementproject.userservice.db.entity.Customer;
import com.advertisementproject.userservice.db.entity.User;
import com.advertisementproject.userservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.userservice.service.interfaces.RegistrationService;
import com.advertisementproject.userservice.service.interfaces.UserService;
import com.advertisementproject.userservice.service.interfaces.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Registration Service implementation that registers customer users or company users, as well as retrieves email
 * details. This service enables registration controller to function.
 */
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final ValidationService validationService;

    /**
     * Registers a customer user using the information supplied in the registration request. The user and customer object
     * are validated before saved in the database.
     *
     * @param registrationRequest request object with all information needed to register a customer user.
     * @return the newly registered customer user
     */
    @Override
    @Transactional
    public CustomerUserResponse registerCustomer(CustomerRegistrationRequest registrationRequest) {
        userService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Customer customer = Customer.toCustomer(user.getId(), registrationRequest);
        validationService.validateCustomer(customer);

        return userService.saveCustomerUser(user, customer);
    }

    /**
     * Registers a company user using the information supplied in the registration request. The user and company object
     * are validated before saved in the database.
     *
     * @param registrationRequest request object with all information needed to register a company user.
     * @return the newly registered company user
     */
    @Override
    @Transactional
    public CompanyUserResponse registerCompany(CompanyRegistrationRequest registrationRequest) {
        userService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Company company = Company.toCompany(user.getId(), registrationRequest);
        validationService.validateCompany(company);

        return userService.saveCompanyUser(user, company);
    }

    /**
     * Retrieves email details for a customer/company user with the supplied email
     *
     * @param email the email for which to retrieve email details
     * @return email details for the supplied email
     */
    @Override
    public EmailDetailsMessage getEmailDetails(String email) {
        Object userInfo = userService.getFullUserInfoByEmail(email);
        UUID userId;
        String name;
        if (userInfo instanceof CustomerUserResponse) {
            CustomerUserResponse customerUser = (CustomerUserResponse) userInfo;
            userId = customerUser.getUser().getId();
            name = customerUser.getCustomer().getFirstName() + " " + customerUser.getCustomer().getLastName();
        } else if (userInfo instanceof CompanyUserResponse) {
            CompanyUserResponse companyUser = (CompanyUserResponse) userInfo;
            userId = companyUser.getUser().getId();
            name = companyUser.getCompany().getName();
        } else throw new IllegalStateException("Invalid user type");

        return new EmailDetailsMessage(userId, name, email);
    }

}
