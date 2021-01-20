package com.advertisementproject.zuulgateway.api;

import com.advertisementproject.zuulgateway.db.models.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    /*
        This UserController manages Authentication for login and also the registration flow.
        All Requests should be handled with POJO's to ensure data-hiding and to not expose sensitive
        data concerning a user. All POJO's should have constrains
     */

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(principal);
    }

}

