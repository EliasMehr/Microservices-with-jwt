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

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final ValidationService validationService;


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

    @Override
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