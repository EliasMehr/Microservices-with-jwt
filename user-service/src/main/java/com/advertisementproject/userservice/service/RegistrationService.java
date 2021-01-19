package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.db.models.Company;
import com.advertisementproject.userservice.db.models.Customer;
import com.advertisementproject.userservice.db.models.User;
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
    private final ValidationService validationService;

    @Transactional
    public User registerCustomer(CustomerRegistrationRequest registrationRequest) {
        validationService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Customer customer = Customer.toCustomer(user.getId(), registrationRequest);
        validationService.validateCustomer(customer);

        userRepository.save(user);
        customerRepository.save(customer);
        //TODO send request to email service to send email for validation
        return user;
    }

    @Transactional
    public User registerCompany(CompanyRegistrationRequest registrationRequest) {
        validationService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Company company = Company.toCompany(user.getId(), registrationRequest);
        validationService.validateCompany(company);

        userRepository.save(user);
        companyRepository.save(company);
        return user;
    }
}
