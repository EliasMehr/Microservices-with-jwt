package com.advertisementproject.userservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @GetMapping
    public ResponseEntity<String> registerUser(){
        return ResponseEntity.ok("It works!!!!!");
    }

}
