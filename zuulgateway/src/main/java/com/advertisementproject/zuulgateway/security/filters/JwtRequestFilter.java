package com.advertisementproject.zuulgateway.security.filters;

import com.advertisementproject.zuulgateway.api.exceptions.ResponseException;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.security.configuration.UserDetailsImpl;
import com.advertisementproject.zuulgateway.security.configuration.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String subject = "";
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            authorizationHeader = authorizationHeader.substring(7);


            subject = jwtUtils.extractSubject(authorizationHeader);

            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

            if (jwtUtils.validateToken(authorizationHeader, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        null);

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }


        } catch (ServletException  | IOException | ResponseException e) {
            response.sendError(555, e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
