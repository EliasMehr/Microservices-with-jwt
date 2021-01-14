package com.advertisementproject.userservice.api;

import com.advertisementproject.userservice.api.request.RegistrationRequest;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping("/")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        logger.info("REQ TYPE " + registrationRequest.getCompanyType().toString());
        //TODO Add more CRUD endpoints for UserController and add antMatchers to Zuul accordingly
        //TODO Remove User entity, registrationRequest etc from Zuul and replace User in UserDetailsImpl with UserCredentials
        //TODO Make Zuul get User info from the User Service?
        //TODO Verify the new registration and login flow
        //TODO fix custom message for enum
        //TODO write lots of tests!
        //TODO Add email messaging validation service
        User user = userService.registerUser(registrationRequest);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable("id") UUID id, @Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
}
