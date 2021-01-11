package com.advertisementproject.userservice.api;

import com.advertisementproject.userservice.api.request.RegistrationRequest;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final RegistrationService registrationService;

    @PostMapping("register")
    public HttpStatus registerUser(@RequestBody RegistrationRequest registrationRequest){
        registrationService.registerUser(registrationRequest);

        return HttpStatus.OK;
    }

}
