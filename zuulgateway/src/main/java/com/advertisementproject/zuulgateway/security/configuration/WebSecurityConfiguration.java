package com.advertisementproject.zuulgateway.security.configuration;

import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.security.filters.JwtTokenValidationFilter;
import com.advertisementproject.zuulgateway.security.filters.JwtUsernameAndPasswordAuthenticationFilter;
import com.advertisementproject.zuulgateway.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

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

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }






}
