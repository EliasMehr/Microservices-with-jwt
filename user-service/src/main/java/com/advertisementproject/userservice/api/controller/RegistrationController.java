package com.advertisementproject.userservice.api.controller;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.messagebroker.dto.ConfirmationTokenMessage;
import com.advertisementproject.userservice.messagebroker.publisher.MessagePublisher;
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
    private final MessagePublisher messagePublisher;
    private final ConfirmationTokenService confirmationTokenService;
    private final PermissionsService permissionsService;

    @PostMapping("customer")
    public ResponseEntity<CustomerUserResponse> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
        CustomerUserResponse customerUser = registrationService.registerCustomer(registrationRequest);

        messagePublisher.sendMessage("confirmationToken",
                new ConfirmationTokenMessage(
                        customerUser.getUser().getId(),
                        customerUser.getCustomer().getFirstName() + " " + customerUser.getCustomer().getLastName(),
                        customerUser.getUser().getEmail()));

        return ResponseEntity.ok(customerUser);
    }

    @PostMapping("company")
    public ResponseEntity<CompanyUserResponse> registerCompany(@Valid @RequestBody CompanyRegistrationRequest registrationRequest) {
        CompanyUserResponse companyUser = registrationService.registerCompany(registrationRequest);

        messagePublisher.sendMessage("confirmationToken",
                new ConfirmationTokenMessage(
                        companyUser.getUser().getId(),
                        companyUser.getCompany().getName(),
                        companyUser.getUser().getEmail()));

        return ResponseEntity.ok(companyUser);
    }

    //TODO move to confirmation token service
    @GetMapping("confirm/{token}")
    public ResponseEntity<String> confirm(@PathVariable String token) {
        UUID userId = confirmationTokenService.confirmTokenAndGetUserId(token);
        registrationService.enableUser(userId);
        permissionsService.createPermissions(userId);
        return ResponseEntity.ok("Your email has been confirmed");
    }
}
