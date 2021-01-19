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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final ValidationService validationService;
    private final EmailService emailService;

    @Transactional
    public User registerCustomer(CustomerRegistrationRequest registrationRequest) {
        validationService.validateNotAlreadyRegistered(registrationRequest.getEmail());

        User user = User.toUser(registrationRequest);
        validationService.validateUser(user);

        Customer customer = Customer.toCustomer(user.getId(), registrationRequest);
        validationService.validateCustomer(customer);
        userRepository.save(user);
        customerRepository.save(customer);

        String token = confirmationTokenService.generateAndSaveToken(user);
        emailService.send(
                user.getEmail(),
                customer.getFirstName() + " " + customer.getLastName(),
                token);

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

        String token = confirmationTokenService.generateAndSaveToken(user);
        emailService.send(
                user.getEmail(),
                company.getName(),
                token);

        return user;
    }

    @Transactional
    public void enableUser(UUID userId) {
        userRepository.enableUser(userId);
    }


}
