package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.EmailAlreadyRegisteredException;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.models.types.Role;
import com.advertisementproject.userservice.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.advertisementproject.userservice.db.models.types.Role.CUSTOMER;

@Service
@RequiredArgsConstructor
@Validated
public class ValidationService {

    private final UserRepository userRepository;

    public void validateNotAlreadyRegistered(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email is already registered for email: " + email);
        }
    }

    public void validateUser(@Valid @RequestBody User user) {
        if (!isValidIdentificationNumber(user.getIdentificationNumber(), user.getRole())) {
            throw new IllegalArgumentException("Invalid identificationNumber");
        }
    }

    private boolean isValidIdentificationNumber(String identificationNumber, Role role){
        //TODO Fix actual validation!!!
        //Would have to be changed if more roles are added
        return role == CUSTOMER ? isValidPersonalNumber(identificationNumber) : isValidOrganizationNumber(identificationNumber);
    }

    private boolean isValidPersonalNumber(String personalNumber){

        return true;
    }

    private boolean isValidOrganizationNumber(String organizationNumber){

        return true;
    }
}
