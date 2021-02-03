package com.advertisementproject.campaignservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Campaign Service Application manages campaigns and gets updates from the User Service Application about companies.
 * Each campaign must be created by a company and company information will be displayed alongside the company in
 * response to requests. Most endpoints will only be open to users with the COMPANY or ADMIN role but there are some
 * endpoints with limited information that are open to CUSTOMER users or even the public.
 * There are scheduled jobs to automatically publish campaigns that are set to be published as well as to remove
 * expired campaigns from the database.
 * Registers with Eureka via @EnableDiscoveryClient. All controller access rights are defined by the Zuul Gateway
 * application.
 *
 * @author Elias Al-Mehr, Jessie Eurenius, Daniel Hughes
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CampaignServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampaignServiceApplication.class, args);
    }

}
