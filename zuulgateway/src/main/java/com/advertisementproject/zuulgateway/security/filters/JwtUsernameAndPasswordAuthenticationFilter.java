package com.advertisementproject.zuulgateway.security.filters;

import com.advertisementproject.zuulgateway.api.request.AuthenticationRequest;
import com.advertisementproject.zuulgateway.api.response.AuthenticationResponse;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.security.model.UserDetailsImpl;
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

import static com.advertisementproject.zuulgateway.security.Utils.ServletResponseUtility.sendErrorResponse;
import static com.advertisementproject.zuulgateway.security.Utils.ServletResponseUtility.sendResponse;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Filter that is run when a user goes to the "/login" endpoint. Validates that a client has entered the correct email
 * and password matching a user in the database, which is an enabled user with permissions.
 */
@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;

    /**
     * Attempts authentication for the supplied username (email) and password in the request.
     * @param request the request, which should contain username (email) and password.
     * @param response the response to be sent
     * @return Authentication object which can be successful or unsuccessful depending on the login attempt
     * @throws AuthenticationException if the Authentication object is invalid for whatever reason
     */
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

    /**
     * Defines what happens when a login attempt is successful. Creates a token based on user details and sends that
     * token along with the user role as a successful login response.
     * @param request the request, containing a correct username (email) and password.
     * @param response the response to be sent
     * @param chain the filter chain that this filter is in
     * @param authResult the authentication object as a result of a successful authentication
     * @throws IOException if any data reading/writing errors occurs
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(authResult.getName());
        String token = jwtUtils.createToken(userDetails);

        AuthenticationResponse authResponse = new AuthenticationResponse(token, userDetails.getUser().getRole());
        sendResponse(response, OK.value(), authResponse);
    }

    /**
     * Sends an error message if any type of exception occurred during the login process.
     * @param request the request, possibly containing faulty or missing credentials, but can also be valid
     * @param response the response to be sent
     * @param failed the authentication object as a result of a failed authentication attempt
     * @throws IOException if any data reading/writing errors occurs
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {

        sendErrorResponse(response, failed.getMessage(), SC_UNAUTHORIZED);
    }
}