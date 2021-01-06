package com.advertisementproject.zuulgateway.security.Utils;

import com.advertisementproject.zuulgateway.api.request.AuthenticationRequest;
import com.advertisementproject.zuulgateway.api.response.AuthenticationResponse;
import com.advertisementproject.zuulgateway.security.configuration.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements AuthenticationProvider {

    private final JwtUtils jwtUtils;

    public JwtUsernameAndPasswordAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(
                            request.getInputStream(),
                            AuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),  // Principal
                    authenticationRequest.getPassword()); // Credentials

            return authenticate(authentication);
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getDetails();
        String token = jwtUtils.generateToken(userDetails);
        AuthenticationResponse authResponse = new AuthenticationResponse(token);
        sendResponse(response, HttpStatus.OK.value(), authResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        sendResponse(response, HttpStatus.UNAUTHORIZED.value(), failed.getMessage());
    }


    private <T> void sendResponse(HttpServletResponse response,
                                  Integer status,
                                  T responseBody) throws IOException {

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
        response.getOutputStream().flush();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
