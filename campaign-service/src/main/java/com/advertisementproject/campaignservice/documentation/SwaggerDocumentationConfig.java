package com.advertisementproject.campaignservice.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Configuration file for Swagger Documentation. Automatically generates api documentation for all endpoints as well as
 * transfer objects, i.e. request or response objects
 */
@Configuration
public class SwaggerDocumentationConfig {

    private static final Contact DEFAULT_CONTACT = new Contact("Campaigns Company", "campaign-project.com", "daniel.hughes@yh.nackademin.se");
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Collections.singletonList("application/json"));

    @Bean
    public Docket api() {
        ApiInfo DEFAULT_API_INFO = new ApiInfo(
                "Campaign Service Api Documentation",
                "Management service for campaigns",
                "1.0",
                null,
                DEFAULT_CONTACT,
                null,
                null,
                new ArrayList<>());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.advertisementproject.campaignservice"))
                .paths(PathSelectors.any())
                .build();
    }
}