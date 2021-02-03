package com.advertisementproject.userservice.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

/**
 * Password encoder configuration
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Password encoder configuration bean
     * @return configured Bcrypt password encoder
     */
    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }

}
