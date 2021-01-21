package com.advertisementproject.zuulgateway.security.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
@RequiredArgsConstructor
@Component("userSecurity")
public class UserSecurity {

private final JwtUtils jwtUtils;
private final HttpServletRequest request;

    public boolean isSameIdAsHeader(UUID userId){

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            return false;
        }
        String token = header.replace("Bearer ", "");
        String subject = jwtUtils.extractSubject(token);

        return UUID.fromString(subject).equals(userId);
    }
}
