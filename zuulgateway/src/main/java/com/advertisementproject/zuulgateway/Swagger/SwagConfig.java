package com.advertisementproject.zuulgateway.Swagger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
public class SwagConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("campaign-service", "/campaign/v3/api-docs"));
        resources.add(swaggerResource("confirmation-token-service", "/confirmation-token/v3/api-docs"));
        resources.add(swaggerResource("zuulgateway", "/v3/api-docs"));
        resources.add(swaggerResource("user-service", "/user/v3/api-docs"));
        resources.add(swaggerResource("permissions-service", "/permissions/v3/api-docs"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }

}