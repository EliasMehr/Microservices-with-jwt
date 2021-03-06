package com.advertisementproject.zuulgateway.security.configuration;

import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.security.filters.JwtTokenValidationFilter;
import com.advertisementproject.zuulgateway.security.filters.JwtUsernameAndPasswordAuthenticationFilter;
import com.advertisementproject.zuulgateway.services.UserDetailsServiceImpl;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Web security configuration for the entire project. CSRF is disabled and cors is activated. Requests can be set as
 * open to the public, only available for non logged in users or requires authorization for one or more specific role(s).
 * If an authorized endpoint is desired, the user must login via "/login" endpoint, which is handled by
 * JwtUsernameAndPasswordAuthenticationFilter. Upon successful login, the user can set the jwt token from the response
 * in the header of the request to the authentication locked endpoint. Then JwtTokenValidationFilter makes sure that the
 * id from the jwt token in the header matches a valid user that is enabled, has permissions and the correct role.
 * <p>
 * Session management is stateless.
 */
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Service that handles JWT related tasks such as extracting the subject from a JWT token.
     */
    private final JwtUtils jwtUtils;

    /**
     * Service for managing user details, which is checked to see that a user exists, is enabled and has appropriate
     * permission.
     */
    private final UserDetailsServiceImpl userDetailsService;


    /**
     * Configures security for the gateway application and thereby the entire system. Cross Site Request Forgery (CSRF)
     * is disabled. Cross Origin Resource Sharing (CORS) is enabled, allowing requests from other origins.
     * AntMatchers specify which endpoints each user role has access to, which endpoints are open to the public and
     * which require that the user is not logged in.
     * Session is set to stateless.
     * A filter is run for each request that requires authentication to make sure that the jwt token is supplied in the
     * header for a valid user that is enabled and has permissions.
     * When a user goes to the "/login" endpoint, JwtUsernameAndPasswordAuthenticationFilter is run to verify the
     * credentials that the user supplied to see if the user can be authenticated.
     *
     * Worth noting is that attaching the user id to the request header is done in ZuulRequestFilter, not here.
     * @param http HttpSecurity object used to configure security for the application.
     * @throws Exception any kind of exception that may occur during any request.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()

                .antMatchers("/me")
                .hasAnyAuthority("COMPANY", "CUSTOMER")

                .antMatchers("/**/v*/api-docs", "/swagger-resources/**", "/webjars/**", "/swagger-ui/**")
                .permitAll()

                .antMatchers("/user/register/**")
                .anonymous()

                .antMatchers("/confirmation-token/**")
                .anonymous()

                .antMatchers("/user")
                .hasAnyAuthority("CUSTOMER", "COMPANY")

                .antMatchers(HttpMethod.GET, "/campaign/all/published")
                .permitAll()

                .antMatchers("/campaign/discount-code/{campaignId:^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$}")
                .hasAnyAuthority("CUSTOMER, COMPANY")

                .antMatchers("/campaign",
                        "/campaign/{campaignId:^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$}")
                .hasAuthority("COMPANY")

                .antMatchers("**")
                .hasAuthority("ADMIN")

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(jwtUtils, userDetailsService, authenticationManager()))
                .addFilterAfter(new JwtTokenValidationFilter(jwtUtils, userDetailsService), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests();


    }

    /**
     * Configuration for using a custom user details service
     *
     * @param auth authentication manager builder for which to set a custom user details service
     * @throws Exception if an exception occurs
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * CorsConfigurationSource configuration bean
     *
     * @return CorsConfigurationSource that accepts any URL source and applies permit values
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type", "X-Total-Count", "Content-Range"));
        configuration.setExposedHeaders(ImmutableList.of("Content-Range", "X-Total-Count"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}