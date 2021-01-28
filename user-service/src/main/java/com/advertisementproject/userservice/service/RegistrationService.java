package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.db.models.Company;
import com.advertisementproject.userservice.db.models.Customer;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.repository.CompanyRepository;
import com.advertisementproject.userservice.db.repository.CustomerRepository;
import com.advertisementproject.userservice.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;
    private final ValidationService validationService;
    private final EmailService emailService;

    @Transactional
    public CustomerUserResponse registerCustomer(CustomerRegistrationRequest registrationRequest) {
        userService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Customer customer = Customer.toCustomer(user.getId(), registrationRequest);
        validationService.validateCustomer(customer);
        userRepository.save(user);
        customerRepository.save(customer);

//        String token = confirmationTokenService.generateAndSaveToken(user);
//        emailService.send(
//                user.getEmail(),
//                customer.getFirstName() + " " + customer.getLastName(),
//                token);
//
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

//        String token = confirmationTokenService.generateAndSaveToken(user);
//        emailService.send(
//                user.getEmail(),
//                company.getName(),
//                token);

        return new CompanyUserResponse(user, company);
    }

    @Transactional
    public void enableUser(UUID userId) {
        userRepository.enableUser(userId);
    }


}
