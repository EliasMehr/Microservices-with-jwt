package com.advertisementproject.confirmationtokenservice.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger configuration file. Defines a docket bean that includes basic configurations such as base package as well as
 * API information to display.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Docket configuration bean
     * @return docket with basic configuration and api information
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.advertisementproject"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Defines api information for swagger api documentation
     * @return ApiInfo object with hard coded api information for this application
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Confirmation Token Service API")
                .description("Confirmation Token Service manages confirmation tokens for users. " +
                        "Tokens are created when new users are created and the confirmation link they receive connects to the endpoint for confirming tokens. " +
                        "This API is normally only accessed via users clicking the confirmation link. " +
                        "This service communicates with other services via message broker.")
                .contact(new Contact("Campaign Company", "http://campaign-company.com", "info@campaign-company.com"))
                .version("3.0")
                .build();
    }
}