package com.advertisementproject.zuulgateway.security.configuration;

import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.security.filters.JwtTokenValidationFilter;
import com.advertisementproject.zuulgateway.security.filters.JwtUsernameAndPasswordAuthenticationFilter;
import com.advertisementproject.zuulgateway.services.UserDetailsServiceImpl;
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
 *
 * Session management is stateless.
 */
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()

                .antMatchers("/me")
                    .hasAnyAuthority("COMPANY", "CUSTOMER")

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
     * @param auth authentication manager builder for which to set a custom user details service
     * @throws Exception if an exception occurs
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * CorsConfigurationSource configuration bean
     * @return CorsConfigurationSource that accepts any URL source and applies default permit values
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}