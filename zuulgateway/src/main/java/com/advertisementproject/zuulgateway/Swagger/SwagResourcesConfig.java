package com.advertisementproject.zuulgateway.Swagger;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger resources configuration that aggregates swagger documentation for all services by fetching their api docs.
 * Swagger API documentation for all services can be viewed at "http://localhost:8080/swagger-ui/".
 */
@Component
@Primary
public class SwagResourcesConfig implements SwaggerResourcesProvider {

    /**
     * Provides swagger resources to be aggregated and displayed at "http://localhost:8080/swagger-ui/".
     * @return list of SwaggerResource objects that are built from the services' JSON api docs.
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("campaign-service", "/campaign"));
        resources.add(swaggerResource("user-service", "/user"));
        resources.add(swaggerResource("permissions-service", "/permissions"));
        resources.add(swaggerResource("confirmation-token-service", "/confirmation-token"));
        resources.add(swaggerResource("zuul-gateway", ""));
        return resources;
    }

    /**
     * Factory method to build a SwaggerResource using display name for the drop-down list, location of the api docs
     * for the service and also declares Swagger version.
     * @param name the name of the service to appear in the drop-down list at "http://localhost:8080/swagger-ui/"
     * @param routeName the route name for the service to build a SwaggerResource for.
     * @return SwaggerResource that allows showing Swagger api documentation for a service
     */
    private SwaggerResource swaggerResource(String name, String routeName) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(routeName + "/v3/api-docs");
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }

}