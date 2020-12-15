package com.advertisementproject.msproductclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class MsProductClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProductClientApplication.class, args);
    }

}
