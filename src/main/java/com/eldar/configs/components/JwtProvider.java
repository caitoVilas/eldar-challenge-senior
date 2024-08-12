package com.eldar.configs.components;

import com.eldar.persistence.entities.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;

/**
 * @author caito Vilas
 * date 08/2024
 * This class is responsible for generating and validating JWT tokens.
 */
@Component
@Slf4j
public class JwtProvider {
    private static final String SECRET_KEY = "Hw9z1Yk8Nmq1IzlwcCg8j6yHzw6RKjzZUi9r7Ww555o0PP";
    private static final long EXPIRATION_TIME = 3600000;

    /**
     * Generates a JWT token based on the user's authentication.
     *
     * @param authentication the user's authentication.
     * @return the generated JWT token.
     */
    public String generateToken(Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", getRoles(user))
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(generateKey())
                .compact();
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token.
     * @return the username.
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validates the JWT token.
     *
     * @param token the JWT token.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extracts the roles from the user's authentication.
     *
     * @param user the user.
     * @return the roles.
     */
    private List<String> getRoles(UserEntity user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    /**
     * Generates a key based on the secret key.
     *
     * @return the generated key.
     */
    private Key generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
