package com.advertisementproject.confirmationtokenservice.controller;

import com.advertisementproject.confirmationtokenservice.service.ConfirmationTokenService;
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

    @GetMapping("/{token}")
    public ResponseEntity<String> confirm(@PathVariable String token) {
        UUID userId = confirmationTokenService.confirmTokenAndGetUserId(token);
//        registrationService.enableUser(userId);
//        permissionsService.createPermissions(userId);
        return ResponseEntity.ok("Your email has been confirmed");
    }
}
