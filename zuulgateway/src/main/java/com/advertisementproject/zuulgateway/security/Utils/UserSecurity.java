package com.advertisementproject.zuulgateway.security.Utils;

import com.advertisementproject.zuulgateway.db.models.UserDetailsImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;
@RequiredArgsConstructor
@Component("userSecurity")
public class UserSecurity {
private final JwtUtils jwtUtils;
private final HttpServletRequest request;
    public boolean isSameIdAsHeader(UUID userId){
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String subject = jwtUtils.extractSubject(token);
        return UUID.fromString(subject).equals(userId);
    }
}
