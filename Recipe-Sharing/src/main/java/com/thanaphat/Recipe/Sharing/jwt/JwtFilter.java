package com.thanaphat.Recipe.Sharing.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;

    public JwtFilter(@Value("${jwt.secret}") String jwtSecret) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = request.getHeader("Authorization");

        try {
            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.substring(7);

                Claims claims = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();

                String email = (String) claims.get("EMAIL");
                String role = (String) claims.get("ROLE");

                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        email, null, List.of(grantedAuthority)
                );


                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid or expired JWT token\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
