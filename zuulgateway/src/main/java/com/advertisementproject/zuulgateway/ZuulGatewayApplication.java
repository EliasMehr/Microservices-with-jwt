package com.advertisementproject.zuulgateway;

import com.advertisementproject.zuulgateway.ZuulFilter.ZuulRequestFilter;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulGatewayApplication {

    /**
     * Runs the application
     * @param args optional command line arguments that are currently not implemented
     */
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }
    
    @Bean
    public ZuulRequestFilter simpleFilter() {
        return new ZuulRequestFilter(new JwtUtils());
    }

}
