package com.advertisementproject.userservice.api.controller;

import com.advertisementproject.userservice.api.request.UpdateUserRequest;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //TODO fix put!
    //TODO endpoints for customer and company (separate controllers, cascade or SQL views?)
    //TODO delete user etc should have a cascade effect

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.getFullUserInfoById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Object>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserById(@PathVariable("id") UUID id, @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserRequest));
    }


}
