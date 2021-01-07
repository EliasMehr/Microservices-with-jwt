package com.advertisementproject.zuulgateway.api;

import com.advertisementproject.zuulgateway.api.request.RegistrationRequest;
import com.advertisementproject.zuulgateway.api.response.MeResponse;
import com.advertisementproject.zuulgateway.db.models.User;
import com.advertisementproject.zuulgateway.security.configuration.UserDetailsImpl;
import com.advertisementproject.zuulgateway.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
        This UserController manages Authentication for login and also the registration flow.
        All Requests should be handled with POJO's to ensure data-hiding and to not expose sensitive
        data concerning a user. All POJO's should have constrains
     */

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info(SecurityContextHolder.getContext().getAuthentication().toString());
        User user  = ((UserDetailsImpl) principal).getUser();
        MeResponse meResponse = MeResponse.builder()
                .userId(user.getId().toString())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();
        return ResponseEntity.ok(meResponse);
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegistrationRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok(user);
    }
}

