package com.advertisementproject.zuulgateway.security.filters;

import com.advertisementproject.zuulgateway.api.exceptions.ErrorMessage;
import com.advertisementproject.zuulgateway.api.exceptions.ResponseException;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.security.configuration.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.advertisementproject.zuulgateway.security.Utils.ServletResponseUtility.sendResponse;
import static com.advertisementproject.zuulgateway.security.Utils.ServletResponseUtility.toResponseMessage;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, ResponseException {

        String authorizationHeader = request.getHeader("Authorization");
        String subject = "";

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            authorizationHeader = authorizationHeader.substring(7);
            subject = jwtUtils.extractSubject(authorizationHeader);
        } catch (Exception e) {
            ErrorMessage responseMessage = toResponseMessage(e.getMessage(), SC_UNAUTHORIZED);
            sendResponse(response, responseMessage.getStatusCode(), responseMessage);
            return;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

        if (jwtUtils.validateToken(authorizationHeader, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    null);

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
