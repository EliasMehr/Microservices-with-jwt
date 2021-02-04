package com.advertisementproject.userservice.api.controller;

import com.advertisementproject.userservice.api.request.UpdateUserRequest;
import com.advertisementproject.userservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<Object>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping
    public ResponseEntity<Object> getUserById(@RequestHeader("userId") UUID id) {
        return ResponseEntity.ok(userService.getFullUserInfoById(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserById(@RequestHeader("userId") UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping
    public ResponseEntity<Object> updateUserById(@RequestHeader("userId") UUID id, @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserRequest));
    }

}
