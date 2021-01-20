package com.advertisementproject.zuulgateway.security.filters;

import com.advertisementproject.zuulgateway.api.request.AuthenticationRequest;
import com.advertisementproject.zuulgateway.api.response.AuthenticationResponse;
import com.advertisementproject.zuulgateway.db.models.UserDetailsImpl;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.advertisementproject.zuulgateway.security.Utils.ServletResponseUtility.sendResponse;
import static com.advertisementproject.zuulgateway.security.Utils.ServletResponseUtility.toErrorResponse;
import static com.advertisementproject.zuulgateway.security.Utils.ServletResponseUtility.sendErrorResponse;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest =
                    new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),  // Principal
                    authenticationRequest.getPassword()); // Credentials

            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(authResult.getName());
        String token = jwtUtils.createToken(userDetails);

        AuthenticationResponse authResponse = new AuthenticationResponse(token);
        sendResponse(response, OK.value(), authResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {

        sendErrorResponse(response, toErrorResponse(failed.getMessage(), SC_UNAUTHORIZED));
    }

}
