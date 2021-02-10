package com.advertisementproject.confirmationtokenservice.controller;

import com.advertisementproject.confirmationtokenservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.confirmationtokenservice.service.interfaces.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller for confirming tokens and informing other microservices about it.
 */
@RestController
@RequiredArgsConstructor
public class ConfirmationTokenController {

    private final ConfirmationTokenService confirmationTokenService;
    private final MessagePublisher messagePublisher;

    /**
     * Endpoint to handle confirming a token when a user clicks the confirmation link in an email.
     *
     * @param token the token to be confirmed.
     * @return a message that the email address has been confirmed.
     */
    @GetMapping("/{token}")
    public ResponseEntity<String> confirm(@PathVariable String token) {
        UUID userId = confirmationTokenService.confirmTokenAndGetUserId(token);
        messagePublisher.sendUserIdMessage("enableUser", userId);
        messagePublisher.sendUserIdMessage("permissionAdd", userId);
        return ResponseEntity.ok("Your email has been confirmed");
    }
}
