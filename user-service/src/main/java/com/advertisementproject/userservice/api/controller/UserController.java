package com.advertisementproject.userservice.api.controller;

import com.advertisementproject.userservice.api.request.UpdateUserRequest;
import com.advertisementproject.userservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for managing CRUD operations for customer users and company users, apart from registration which is
 * handled by the registration controller.
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint for ADMIN users to receive a list of all users
     * @return response entity with a list of all users in the database
     */
    @GetMapping("/all")
    public ResponseEntity<List<Object>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    /**
     * Lets a user retrieve user and customer/company information via user id supplied in the header
     * @param id the id for the currently logged in user to be retrieved, provided by Zuul Gateway in the header.
     * @return customer user or company user info for the supplied id
     */
    @GetMapping
    public ResponseEntity<Object> getUserById(@RequestHeader("userId") UUID id) {
        return ResponseEntity.ok(userService.getFullUserInfoById(id));
    }

    /**
     * Lets a user delete their own user information
     * @param id the id for the currently logged in user to be deleted, provided by Zuul Gateway in the header.
     * @return
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUserById(@RequestHeader("userId") UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted");
    }

    /**
     * Lets a user update their own user information
     * @param id the id for the currently logged in user that should be updated, provided by Zuul Gateway in the header.
     * @param updateUserRequest request object with fields to update in the user and related customer/company
     * @return customer user or company user that has just been updated
     */
    @PutMapping
    public ResponseEntity<Object> updateUserById(@RequestHeader("userId") UUID id, @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserRequest));
    }

}
