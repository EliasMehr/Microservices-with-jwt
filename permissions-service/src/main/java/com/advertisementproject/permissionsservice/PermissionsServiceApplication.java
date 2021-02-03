package com.advertisementproject.permissionsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Permissions Service Application is a microservice for managing user permissions and informing other microservices
 * about the state of user permissions. The controller endpoints are intended for ADMIN users only as that is a
 * restricted privilege normal users should not have access to.
 *
 * Registers with Eureka via @EnableDiscoveryClient. All controller access rights are defined by the Zuul Gateway
 * application.
 *
 * @author Elias Al-Mehr, Jessie Eurenius, Daniel Hughes
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PermissionsServiceApplication {

    /**
     * Runs the application
     * @param args optional command line arguments that are currently not implemented
     */
    public static void main(String[] args) {
        SpringApplication.run(PermissionsServiceApplication.class, args);
    }

}
