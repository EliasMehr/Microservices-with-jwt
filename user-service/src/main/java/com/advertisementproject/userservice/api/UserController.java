package com.advertisementproject.userservice.api;

import com.advertisementproject.userservice.api.request.RegistrationRequest;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.models.types.CompanyType;
import com.advertisementproject.userservice.service.RegistrationService;
import com.google.common.base.Enums;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.enums.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final RegistrationService registrationService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping("register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {

        logger.info("REQ TYPE " + registrationRequest.getType().toString());
        //TODO fix custom message for enum
        //TODO fix validation for identificationNumber
        //TODO fix proper error message when trying to register with an already existing email
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
