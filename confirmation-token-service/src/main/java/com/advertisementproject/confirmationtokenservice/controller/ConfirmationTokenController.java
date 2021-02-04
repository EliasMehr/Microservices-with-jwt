package com.advertisementproject.confirmationtokenservice.controller;

import com.advertisementproject.confirmationtokenservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.confirmationtokenservice.service.interfaces.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ConfirmationTokenController {

    private final ConfirmationTokenService confirmationTokenService;
    private final MessagePublisher messagePublisher;

    @GetMapping("/{token}")
    public ResponseEntity<String> confirm(@PathVariable String token) {
        UUID userId = confirmationTokenService.confirmTokenAndGetUserId(token);
        messagePublisher.sendUserIdMessage("enableUser", userId);
        messagePublisher.sendUserIdMessage("permissionsAdd", userId);
        return ResponseEntity.ok("Your email has been confirmed");
    }
}
