package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.db.model.Company;
import com.advertisementproject.userservice.db.model.Customer;
import com.advertisementproject.userservice.db.model.User;
import com.advertisementproject.userservice.messagebroker.dto.EmailDetailsMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final ValidationService validationService;


    @Transactional
    public CustomerUserResponse registerCustomer(CustomerRegistrationRequest registrationRequest) {
        userService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Customer customer = Customer.toCustomer(user.getId(), registrationRequest);
        validationService.validateCustomer(customer);

        return userService.saveCustomerUser(user, customer);
    }

    @Transactional
    public CompanyUserResponse registerCompany(CompanyRegistrationRequest registrationRequest) {
        userService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Company company = Company.toCompany(user.getId(), registrationRequest);
        validationService.validateCompany(company);

        return userService.saveCompanyUser(user, company);
    }

    public EmailDetailsMessage getEmailDetails(String email){
        Object userInfo = userService.getFullUserInfoByEmail(email);
        UUID userId;
        String name;
        if(userInfo instanceof CustomerUserResponse){
            CustomerUserResponse customerUser = (CustomerUserResponse) userInfo;
            userId = customerUser.getUser().getId();
            name = customerUser.getCustomer().getFirstName() + " " + customerUser.getCustomer().getLastName();
        }
        else if(userInfo instanceof CompanyUserResponse){
            CompanyUserResponse companyUser = (CompanyUserResponse) userInfo;
            userId = companyUser.getUser().getId();
            name = companyUser.getCompany().getName();
        }
        else throw new IllegalStateException("Invalid user type");

        return new EmailDetailsMessage(userId, name, email);
    }

}
