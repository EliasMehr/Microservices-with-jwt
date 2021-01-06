package com.advertisementproject.zuulgateway.services;

import com.advertisementproject.zuulgateway.api.request.AuthenticationRequest;
import com.advertisementproject.zuulgateway.api.request.RegistrationRequest;
import com.advertisementproject.zuulgateway.api.response.AuthenticationResponse;
import com.advertisementproject.zuulgateway.db.models.User;
import com.advertisementproject.zuulgateway.db.repositories.UserRepository;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.security.configuration.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public User register(RegistrationRequest request) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .enabled(true)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        repository.save(user);
        return user;
    }
}
