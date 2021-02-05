package com.advertisementproject.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Email Service Application handles sending emails with a confirmation link to users after they register. To accomplish
 * this, the application receives a token message from Confirmation Token Service application and email details
 * message from User Service application. Both messages are tied to a specific user id. Once all the information is
 * gathered for a user id, then an email is sent with a confirmation link, which connects to an endpoint in
 * Confirmation Token Service that confirms the token when a user clicks on the confirmation link.
 *
 * Registers with Eureka via @EnableDiscoveryClient. There are no controllers for this application.
 *
 * @author Elias Al-Mehr, Jessie Eurenius, Daniel Hughes
 */
@EnableDiscoveryClient
@SpringBootApplication
public class EmailServiceApplication {

    /**
     * Runs the application
     * @param args optional command line arguments that are currently not implemented
     */
    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication.class, args);
    }

}
