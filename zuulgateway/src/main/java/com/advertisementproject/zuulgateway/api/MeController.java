package com.advertisementproject.zuulgateway.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for a user to view the their logged in security status from the security context holder
 */
@RequiredArgsConstructor
@RestController
public class MeController {


    /**
     * Lets a logged in user see the security principal that they are logged in with. Includes user information.
     * @return principal from the security context holder
     */
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(principal);
    }

}

