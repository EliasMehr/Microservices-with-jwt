package com.advertisementproject.zuulgateway.services;

import com.advertisementproject.zuulgateway.api.exceptions.RegistrationException;
import com.advertisementproject.zuulgateway.api.request.RegistrationRequest;
import com.advertisementproject.zuulgateway.db.models.User;
import com.advertisementproject.zuulgateway.db.models.types.CompanyType;
import com.advertisementproject.zuulgateway.db.models.types.Role;
import com.advertisementproject.zuulgateway.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.advertisementproject.zuulgateway.db.models.User.toUser;
import static com.advertisementproject.zuulgateway.db.models.types.Role.*;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    public User register(RegistrationRequest request) {
        // 1. Check if email is already registered
        validateNotRegistered(request.getEmail());
        // 2. Create Customer or Organization depending on request that's filled in
        User user = toCustomerOrCompany(request);
        // 3. Call AccountValidationService to create a auto generated code to confirm account by email
        // 3.1 If account is validated, create permissions for user
        // 4. Persist user into db
        repository.save(user);

        return null;
    }

    private void validateNotRegistered(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email is already registered");
        }
    }

    private User toCustomerOrCompany(RegistrationRequest request) {
        Role role = request.getType() == CompanyType.NOT_SPECIFIED ? CUSTOMER : ORGANIZATION;
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        return toUser(request, role, hashedPassword);
    }

}
