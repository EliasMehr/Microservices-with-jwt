package com.advertisementproject.zuulgateway.security.filters;

import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        try {
            if (authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String subject = jwtUtils.extractSubject(authorizationHeader);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    subject,
                    null,
                    null);

            SecurityContextHolder.getContext().setAuthentication(authentication);


        } catch (IOException | ServletException e) {
            response.sendError(SC_FORBIDDEN);
        }
        filterChain.doFilter(request, response);
    }
}
