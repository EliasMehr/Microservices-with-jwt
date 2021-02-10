package com.advertisementproject.zuulgateway.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

/**
 * Configuration for BCrypt password encoder
 */
@Configuration
public class BCryptPassEncoderConfig {

    /**
     * BCrypt password encoder configuration bean
     *
     * @return BCrypt password encoder
     */
    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }
}
