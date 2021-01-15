package com.advertisementproject.zuulgateway.security.filters;

import com.advertisementproject.zuulgateway.api.exceptions.ErrorMessage;
import com.advertisementproject.zuulgateway.api.exceptions.RegistrationException;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.services.UserDetailsImpl;
import com.advertisementproject.zuulgateway.services.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class JwtTokenValidationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException, RegistrationException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String userId;
        String token = authorizationHeader.replace("Bearer ", "");

        try {
            userId = jwtUtils.extractSubject(token);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (JwtException e) {
            ErrorMessage responseMessage = toResponseMessage(e.getMessage(), SC_UNAUTHORIZED);
            sendResponse(response, responseMessage.getStatusCode(), responseMessage);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
