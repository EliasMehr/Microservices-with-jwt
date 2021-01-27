package com.advertisementproject.userservice.api.controller;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.service.ConfirmationTokenService;
import com.advertisementproject.userservice.service.RegistrationService;
import com.advertisementproject.userservice.service.interfaces.PermissionsService;
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

    //TODO fix custom message for enum
    //TODO write lots of tests!

    private final RegistrationService registrationService;
    private final ConfirmationTokenService confirmationTokenService;
    private final PermissionsService permissionsService;

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
        permissionsService.createPermissions(userId);
        return ResponseEntity.ok("Your email has been confirmed");
    }
}
