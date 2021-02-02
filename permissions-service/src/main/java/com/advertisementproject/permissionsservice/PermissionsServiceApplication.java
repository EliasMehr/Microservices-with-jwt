package com.advertisementproject.permissionsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Permissions Service Application is a microservice for managing user permissions and informing other microservices
 * about the state of user permissions. Registers with Eureka via @EnableDiscoveryClient. The controller endpoints are
 * intended for ADMIN users only as that is a restricted privilege normal users should not have access to.
 *
 * @author Elias Al-Mehr, Jessie Eurenius, Daniel Hughes
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PermissionsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionsServiceApplication.class, args);
    }

}
