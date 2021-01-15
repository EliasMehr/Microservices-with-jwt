package com.advertisementproject.zuulgateway.security.Utils;

import com.advertisementproject.zuulgateway.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtils {

    private final Long EXPIRATION_VALUE = 24L;
    private final String JWT_SECRET = "ABCABCABCABCABCABCABCABCABCABCABCABCABC";

    /*
        To add custom claims, put key and value in the generateToken method!
        for instance -> admin: true or false based on userDetails
     */

    // TODO -> We should insert the users id in the claims to validate all requests through our
    // TODO -> communication layers, we dont want an unauthorized user to retrieve data about another user if that's the case

    public String generateToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUser().getId().toString());
    }

    public String extractSubject(String token) {
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }


    private Date generateExpirationDate() {
        Instant expiry = Instant.now().plus(EXPIRATION_VALUE, ChronoUnit.HOURS);
        return Date.from(expiry);
    }

}
