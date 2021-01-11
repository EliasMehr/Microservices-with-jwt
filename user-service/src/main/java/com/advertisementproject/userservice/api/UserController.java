package com.advertisementproject.userservice.api;

import com.advertisementproject.userservice.api.request.RegistrationRequest;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final RegistrationService registrationService;

    @PostMapping("register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest){

        //TODO fix validation for enums and make sure CompanyType registers correctly (Custom enum validator?)
        //TODO fix validation for identificationNumber
        //TODO fix error handler for constraints so the error details are visible to the client instead of error count
        //TODO Remove User entity, registrationRequest etc from Zuul and replace User in UserDetailsImpl with UserCredentials
        //TODO Make Zuul get User info from the User Service?
        //TODO Verify the new registration and login flow
        //TODO Add more CRUD endpoints for UserController and add antMatchers to Zuul accordingly

        //TODO write lots of tests!

        //TODO Add email messaging validation service


        User user = registrationService.registerUser(registrationRequest);

        return ResponseEntity.ok(user);
    }

}
