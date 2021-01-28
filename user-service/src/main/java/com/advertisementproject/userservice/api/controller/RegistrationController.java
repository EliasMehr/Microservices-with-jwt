package com.advertisementproject.userservice.api.controller;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.messagebroker.dto.UserMessage;
import com.advertisementproject.userservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.userservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register/")
@RequiredArgsConstructor
public class RegistrationController {

    //TODO fix custom message for enum
    //TODO write lots of tests!

    private final RegistrationService registrationService;
    private final MessagePublisher messagePublisher;

    @PostMapping("customer")
    public ResponseEntity<CustomerUserResponse> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest registrationRequest) {
        CustomerUserResponse customerUser = registrationService.registerCustomer(registrationRequest);

        messagePublisher.sendMessage("confirmationToken",
                new UserMessage(
                        customerUser.getUser().getId(),
                        customerUser.getCustomer().getFirstName() + " " + customerUser.getCustomer().getLastName(),
                        customerUser.getUser().getEmail()));

        return ResponseEntity.ok(customerUser);
    }

    @PostMapping("company")
    public ResponseEntity<CompanyUserResponse> registerCompany(@Valid @RequestBody CompanyRegistrationRequest registrationRequest) {
        CompanyUserResponse companyUser = registrationService.registerCompany(registrationRequest);

        messagePublisher.sendMessage("confirmationToken",
                new UserMessage(
                        companyUser.getUser().getId(),
                        companyUser.getCompany().getName(),
                        companyUser.getUser().getEmail()));

        return ResponseEntity.ok(companyUser);
    }
}
