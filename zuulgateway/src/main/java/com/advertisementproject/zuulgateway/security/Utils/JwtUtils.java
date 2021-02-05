package com.advertisementproject.zuulgateway.security.Utils;

import com.advertisementproject.zuulgateway.security.model.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilities class for creating JSON Web Tokens (JWT) and extracting the subject from them
 */
@Service
public class JwtUtils {

    private final Long EXPIRATION_VALUE = 24L;
    private final String JWT_SECRET = "ABCABCABCABCABCABCABCABCABCABCABCABCABC";


    /**
     * Extracts the subject (user id in our case) from a jwt token.
     * @param token the token to extract the subject (user id) from
     * @return the extracted subject (user id)
     * @throws io.jsonwebtoken.ExpiredJwtException if the token is expired
     * @throws io.jsonwebtoken.UnsupportedJwtException if the jwt token format is not supported
     * @throws io.jsonwebtoken.MalformedJwtException if the jwt token is malformed, typically not including exactly 2 periods
     * @throws io.jsonwebtoken.SignatureException if the jwt token signature is invalid
     * @throws IllegalArgumentException if the argument is not a valid string
     */
    public String extractSubject(String token) {
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }

    /**
     * Creates a jwt token that expires in 24 hours with authorities from user details as claims and user id from
     * user details as subject. Signs the token using a HS256 signature algorithm and a secret key.
     * @param userDetails user details to get subject and claims from
     * @return a signed 24 hour token based on user details which can be used to authenticate requests
     */
    public String createToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        String subject = userDetails.getUser().getId().toString();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }


    /**
     * Helper method for generating an expiration date
     * @return expiration timestamp 24 hours later than current timestamp
     */
    private Date generateExpirationDate() {
        Instant expiry = Instant.now().plus(EXPIRATION_VALUE, ChronoUnit.HOURS);
        return Date.from(expiry);
    }

}
