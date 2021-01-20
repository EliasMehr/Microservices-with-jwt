package com.advertisementproject.zuulgateway.security.Utils;

import com.advertisementproject.zuulgateway.db.models.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("userSecurity")
public class UserSecurity {

    public boolean hasUserId(Authentication authentication, UUID userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId().equals(userId);
    }
}
