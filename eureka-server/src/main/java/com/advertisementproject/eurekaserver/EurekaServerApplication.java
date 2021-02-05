package com.advertisementproject.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server Application is an application that relies on spring-cloud-starter-netflix-eureka-server as a
 * dependency. With the annotation @EnableEurekaServer and some configuration in application.yml, the application has
 * all it needs to act as a Eureka server. This means that this application will register and keep track of all our
 * microservices assuming they connect with this application.
 *
 * @author Elias Al-Mehr, Jessie Eurenius, Daniel Hughes
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    /**
     * Runs the application
     * @param args optional command line arguments that are currently not implemented
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
