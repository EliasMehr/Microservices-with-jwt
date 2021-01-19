package com.advertisementproject.zuulgateway.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    /*
        This UserController manages Authentication for login and also the registration flow.
        All Requests should be handled with POJO's to ensure data-hiding and to not expose sensitive
        data concerning a user. All POJO's should have constrains
     */
//    @GetMapping("/me")
//    public ResponseEntity<?> me() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User currentUser = ((UserDetailsImpl) principal).getUser();
//        return ResponseEntity.ok(currentUser);
//    }

}

