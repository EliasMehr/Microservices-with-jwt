package com.advertisementproject.userservice.api.controller;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.userservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.userservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        messagePublisher.sendUserIdMessage("confirmationToken", customerUser.getUser().getId());
        messagePublisher.sendEmailDetailsMessage(
                new EmailDetailsMessage(
                        customerUser.getUser().getId(),
                        customerUser.getCustomer().getFirstName() + " " + customerUser.getCustomer().getLastName(),
                        customerUser.getUser().getEmail()));

        return ResponseEntity.ok(customerUser);
    }

    @PostMapping("company")
    public ResponseEntity<CompanyUserResponse> registerCompany(@Valid @RequestBody CompanyRegistrationRequest registrationRequest) {
        CompanyUserResponse companyUser = registrationService.registerCompany(registrationRequest);

        messagePublisher.sendUserIdMessage("confirmationToken", companyUser.getUser().getId());
        messagePublisher.sendEmailDetailsMessage(
                new EmailDetailsMessage(
                        companyUser.getUser().getId(),
                        companyUser.getCompany().getName(),
                        companyUser.getUser().getEmail()));

        return ResponseEntity.ok(companyUser);
    }

    @GetMapping("resend-email/{email}")
    public ResponseEntity<String> resendEmail(@PathVariable String email){
        EmailDetailsMessage emailDetails = registrationService.getEmailDetails(email);
        messagePublisher.sendUserIdMessage("confirmationToken", emailDetails.getUserId());
        messagePublisher.sendEmailDetailsMessage(emailDetails);
        return ResponseEntity.ok("New confirmation email has been sent");
    }
}
