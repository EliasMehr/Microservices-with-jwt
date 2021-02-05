package com.advertisementproject.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * User Service Application registers and manages users. Each user may have a role as a CUSTOMER, COMPANY or ADMIN.
 * CUSTOMER and COMPANY users will have a customer or company object respectively with the same id as the user id.
 * ADMIN users have total access, COMPANY has access to do more things than a CUSTOMER and CUSTOMER has limited access
 * but more access than a client that is not logged in as a user.
 * In total, this application is responsible for three database tables: users - customer - company
 *
 * Whenever this application registers new entities or changes information, it informs other microservices about it.
 * Currently no other microservice cares about customer information so no update messages are sent for customers.
 *
 * Registers with Eureka via @EnableDiscoveryClient. All controller access rights are defined by the Zuul Gateway
 * application.
 *
 * @author Elias Al-Mehr, Jessie Eurenius, Daniel Hughes
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApplication {

    /**
     * Runs the application
     * @param args optional command line arguments that are currently not implemented
     */
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
