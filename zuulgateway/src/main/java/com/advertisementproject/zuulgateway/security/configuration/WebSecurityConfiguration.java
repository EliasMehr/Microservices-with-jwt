package com.advertisementproject.zuulgateway.security.configuration;

import com.advertisementproject.zuulgateway.api.exceptions.RestAuthenticationEntryPoint;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.security.filters.JwtRequestFilter;
import com.advertisementproject.zuulgateway.security.filters.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/register").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .addFilter(jwtUsernameAndPasswordAuthenticationFilter())
                .addFilterBefore(new JwtRequestFilter(jwtUtils, userDetailsService), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests();
    }

    public JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter() throws Exception {
        JwtUsernameAndPasswordAuthenticationFilter jwtFilter = new JwtUsernameAndPasswordAuthenticationFilter(jwtUtils);
        jwtFilter.setAuthenticationManager(authenticationManagerBean());
        jwtFilter.setFilterProcessesUrl("/api/login");
        return jwtFilter;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean(name = "myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new org.springframework.web.filter.CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }


}
