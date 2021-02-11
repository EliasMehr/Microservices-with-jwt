package com.advertisementproject.zuulgateway.security.filters;

import com.advertisementproject.zuulgateway.security.model.UserDetailsImpl;
import com.advertisementproject.zuulgateway.security.Utils.JwtUtils;
import com.advertisementproject.zuulgateway.services.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.advertisementproject.zuulgateway.security.Utils.ServletResponseUtility.sendErrorResponse;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * A once per request filter that verifies that the user id from the jwt token in the Authorization header is a valid
 * user that is enabled, has permissions and allows the ant matchers in WebSecurityConfiguration to verify the user's
 * role. Creates a UsernamePasswordAuthenticationToken that is set in the SecurityContextHolder with details from the
 * request. Sends error response if something fails authentication via ServletResponseUtility.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenValidationFilter extends OncePerRequestFilter {

    /**
     * Service that handles JWT related tasks such as extracting the subject from a JWT token.
     */
    private final JwtUtils jwtUtils;

    /**
     * Service for managing user details, which is checked to see that a user exists, is enabled and has appropriate
     * permission.
     */
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Runs the filter, verifying that the user id from the jwt token in the Authorization header is a valid
     * user that is enabled, has permissions and allows the ant matchers in WebSecurityConfiguration to verify the user's
     * role. Creates a UsernamePasswordAuthenticationToken that is set in the SecurityContextHolder with details from the
     * request. Sends error response if something fails authentication via ServletResponseUtility.
     *
     * @param request     the request to be filtered
     * @param response    the response to be sent
     * @param filterChain the chain of filters to be continued
     * @throws ServletException if an servlet related exception occurs
     * @throws IOException      if data reading/writing fails
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");

        try {
            String userId = jwtUtils.extractSubject(token);
            UserDetailsImpl userDetails = userDetailsService.loadUserById(UUID.fromString(userId));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (JwtException | UsernameNotFoundException e) {
            sendErrorResponse(response, e.getMessage(), SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
