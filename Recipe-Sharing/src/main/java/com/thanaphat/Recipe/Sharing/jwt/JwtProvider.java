package com.thanaphat.Recipe.Sharing.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtProvider {




    public JwtProvider(@Value("${jwt.secret}") String jwtSecret) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private final SecretKey secretKey;


    public String generateJwt(
            Authentication authentication) {
        String email = authentication.getName();
        String role =
                authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse(null);
        String jwt = Jwts.builder()
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                new Date().getTime() +
                                        86400000))
                .claims(Map.of("ROLE", role,
                        "EMAIL", email))
                .signWith(secretKey).compact();
        return jwt;
    }


    public String getEmailFromJwt(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(jwt)
                .getPayload();
        String email =
                (String) claims.get("EMAIL");
        return email;
    }
}
