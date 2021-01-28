package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.db.model.Company;
import com.advertisementproject.userservice.db.model.Customer;
import com.advertisementproject.userservice.db.model.User;
import com.advertisementproject.userservice.db.repository.CompanyRepository;
import com.advertisementproject.userservice.db.repository.CustomerRepository;
import com.advertisementproject.userservice.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final ValidationService validationService;


    @Transactional
    public CustomerUserResponse registerCustomer(CustomerRegistrationRequest registrationRequest) {
        userService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Customer customer = Customer.toCustomer(user.getId(), registrationRequest);
        validationService.validateCustomer(customer);
        userRepository.save(user);
        customerRepository.save(customer);

        return new CustomerUserResponse(user, customer);
    }

    @Transactional
    public CompanyUserResponse registerCompany(CompanyRegistrationRequest registrationRequest) {
        userService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Company company = Company.toCompany(user.getId(), registrationRequest);
        validationService.validateCompany(company);

        userRepository.save(user);
        companyRepository.save(company);

        return new CompanyUserResponse(user, company);
    }

}
