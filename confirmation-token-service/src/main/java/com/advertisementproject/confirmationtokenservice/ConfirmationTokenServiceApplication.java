package com.advertisementproject.confirmationtokenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Confirmation Token Service Application is a microservice for handling confirmation tokens for users. User Service
 * application informs when a user has been created or deleted, letting Confirmation Token Service Application know
 * when to add a confirmation token or remove confirmation tokens and for which user id. When a token has been created,
 * this application is responsible for informing Email Service application so that an email can be sent.
 *
 * There is a single endpoint for confirming a token. This endpoint should be included in the confirmation link of the
 * email that Email Service application sends to a newly registered user. When a token is confirmed, this application is
 * responsible for informing the Permissions Service application so that permissions can be added for that user id. This
 * application is also responsible to inform User Service that a user should be enabled for that user id.
 *
 * Registers with Eureka via @EnableDiscoveryClient. All controller access rights are defined by the Zuul Gateway
 * application.
 * @author Elias Al-Mehr, Jessie Eurenius, Daniel Hughes
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class ConfirmationTokenServiceApplication {

    /**
     * Runs the application
     * @param args optional command line arguments that are currently not implemented
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfirmationTokenServiceApplication.class, args);
    }

}
