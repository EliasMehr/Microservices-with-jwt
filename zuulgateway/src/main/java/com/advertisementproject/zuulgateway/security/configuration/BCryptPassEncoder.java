package com.advertisementproject.zuulgateway.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class BCryptPassEncoder {

        /*
            BCrypt Password Encoder configured with SecureRandom Algorithm!
            DO NOT CHANGE "strength" to maintain performance
         */

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }
}
