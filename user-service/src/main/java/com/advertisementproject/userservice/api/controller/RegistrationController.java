package com.advertisementproject.userservice.api.controller;

import com.advertisementproject.userservice.api.request.CompanyRegistrationRequest;
import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.messagebroker.dto.EmailDetailsMessage;
import com.advertisementproject.userservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.userservice.service.interfaces.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for registering new users either as a customer user or company user. Sends messages to other microservices
 * to inform them that a user has been registered.
 */
@RestController
@RequestMapping("/register/")
@RequiredArgsConstructor
public class RegistrationController {

    //TODO fix custom message for enum
    //TODO write lots of tests!

    private final RegistrationService registrationService;
    private final MessagePublisher messagePublisher;

    /**
     * Endpoint for registering a customer user. Validates that required fields are not null, registers a customer user
     * and then sends messages to Confirmation Token Service application and Email Service application.
     * @param registrationRequest registration request object including all information needed to register a new user.
     * @return Response entity including the newly created user object and related customer object
     */
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

    /**
     * Endpoint for registering a company user. Validates that required fields are not null, registers a company user
     * and then sends messages to Confirmation Token Service application and Email Service application.
     * @param registrationRequest registration request object including all information needed to register a new user.
     * @return Response entity including the newly created user object and related company object
     */
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

    /**
     * Endpoint for sending a new confirmation link email to the supplied email. Retrieves email details related to the
     * user that has that email address, then sends out messages to Confirmation Token Service application and
     * Email Service application so a new confirmation token can be created and a new email sent to the user.
     * @param email the email address to retrieve email details from and send an email to.
     * @return Response entity with the message that a new email has been sent.
     */
    @GetMapping("resend-email/{email}")
    public ResponseEntity<String> resendEmail(@PathVariable String email){
        EmailDetailsMessage emailDetails = registrationService.getEmailDetails(email);
        messagePublisher.sendUserIdMessage("confirmationToken", emailDetails.getUserId());
        messagePublisher.sendEmailDetailsMessage(emailDetails);
        return ResponseEntity.ok("New confirmation email has been sent");
    }
}
