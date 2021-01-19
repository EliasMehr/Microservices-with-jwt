package com.advertisementproject.userservice.api.controller;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.service.ConfirmationTokenService;
import com.advertisementproject.userservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/register/")
@RequiredArgsConstructor
public class RegistrationController {

    //TODO Add more CRUD endpoints for UserController and add antMatchers to Zuul accordingly
    //TODO Remove User entity, registrationRequest etc from Zuul and replace User in UserDetailsImpl with UserCredentials
    //TODO Make Zuul get User info from the User Service?
    //TODO Verify the new registration and login flow
    //TODO fix custom message for enum
    //TODO write lots of tests!
    //TODO Add email messaging validation service

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RegistrationService registrationService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping("customer")
    public ResponseEntity<User> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
        User user = registrationService.registerCustomer(registrationRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("company")
    public ResponseEntity<User> registerCompany(@Valid @RequestBody CompanyRegistrationRequest registrationRequest) {
        User user = registrationService.registerCompany(registrationRequest);
        return ResponseEntity.ok(user);
    }

    @GetMapping("confirm/{token}")
    public ResponseEntity<String> confirm(@PathVariable String token) {
        UUID userId = confirmationTokenService.confirmTokenAndGetUserId(token);
        registrationService.enableUser(userId);
        return ResponseEntity.ok("Your email has been confirmed");
    }
}
