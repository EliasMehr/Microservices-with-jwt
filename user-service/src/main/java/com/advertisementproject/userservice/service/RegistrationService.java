package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.request.RegistrationRequest;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.models.types.CompanyType;
import com.advertisementproject.userservice.db.models.types.Role;
import com.advertisementproject.userservice.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static com.advertisementproject.userservice.db.models.types.Role.CUSTOMER;
import static com.advertisementproject.userservice.db.models.types.Role.ORGANIZATION;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    public void registerUser(RegistrationRequest registrationRequest) {
        User user = toUser(registrationRequest);
        validationService.validateUser(user);
        userRepository.save(user);
        //TODO validate identification number and make sure any other validation is being done correctly
        //TODO send request to email service to send email for validation
    }

    private User toUser(RegistrationRequest request) {
        Role role = request.getType() == CompanyType.NOT_SPECIFIED ? CUSTOMER : ORGANIZATION;
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        return User.toUser(request, role, hashedPassword);
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }
}
