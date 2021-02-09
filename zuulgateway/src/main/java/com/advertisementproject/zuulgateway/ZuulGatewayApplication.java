package com.advertisementproject.zuulgateway;

import com.advertisementproject.zuulgateway.ZuulFilter.ZuulRequestFilter;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Zuul Gateway Application is responsible for securing the entire project and routing requests to the different
 * microservices. The WebSecurityConfiguration class defines which endpoints each role has access to, which endpoints
 * are only available to non logged in users and which endpoints are open to the public. Since Docker Compose runs all
 * the microservices as containers in a closed network, this application can act as a gateway, restricting access to all
 * other microservices.
 * <p>
 * This application receives updates from via RabbitMQ message broker from User Service application and Permissions
 * Service application about changes to the users and permissions table respectively, which allows this application
 * to keep an up-to-date copy of said database tables.
 * <p>
 * After a user has been registered, they must confirm their email and upon doing so, the user is set to enabled and
 * permissions are created for that user. After that, the user may log in with the email and password they registered
 * with. The endpoint "/login" is automatically configured by Spring Security and when the endpoint is visited,
 * JwtUsernameAndPasswordAuthenticationFilter runs a security check on that email and password, also making sure that
 * the user not only exists, but is enabled and has permissions. If login is successful, the user's role and a
 * jwt bearer token is returned as a response to the login request. The lifetime of the jwt token is currently set to
 * 24 hours.
 * <p>
 * For any other request to an endpoint that is not marked anonymous or open to the public, JwtTokenValidationFilter
 * looks for a jwt bearer token to be in the header. The token must be in valid format, not expired and contain a valid
 * user id. The user and permissions are retrieved from the database via the user id from the token. The user must be
 * a valid user that is enabled and has permissions which has not been revoked. If the security check passes, then
 * ZuulRequestFilter class attaches the user id to the header and the request is forwarded to the desired endpoint,
 * provided that the user has sufficient access rights for that endpoint.
 * <p>
 * Registers with Eureka via @EnableDiscoveryClient. This application is responsible for access rights to all endpoints
 * for all other microservices.
 *
 * @author Elias Al-Mehr, Jessie Eurenius, Daniel Hughes
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableSwagger2
public class ZuulGatewayApplication {

    /**
     * Runs the application
     *
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
