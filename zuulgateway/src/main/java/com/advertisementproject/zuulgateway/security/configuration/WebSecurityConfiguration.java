package com.advertisementproject.zuulgateway.security.configuration;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
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
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/register", "/user/register").permitAll()
                .antMatchers("/me").hasAnyAuthority("ORGANIZATION", "CUSTOMER")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtUsernameAndPasswordAuthenticationFilter())
                .addFilterBefore(new JwtRequestFilter(jwtUtils, userDetailsService), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests();
    }

    public JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter() throws Exception {
        JwtUsernameAndPasswordAuthenticationFilter jwtFilter = new JwtUsernameAndPasswordAuthenticationFilter(jwtUtils, userDetailsService);
        jwtFilter.setAuthenticationManager(authenticationManagerBean());
        jwtFilter.setFilterProcessesUrl("/login");
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
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}
