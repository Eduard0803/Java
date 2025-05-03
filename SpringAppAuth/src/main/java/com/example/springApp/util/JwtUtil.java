package com.example.springApp.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.util.Collections;

import com.example.springApp.entities.UserEntity;

import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    private final String SECRET = "umaChaveSecretaDeExemploQuePrecisaSerMaiorQue32Caracteres";
    private final long EXPIRATION = 1000 * 60 * 1; // 1 minute

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(UserEntity user){
        return Jwts.builder()
            .claim("id", user.getId())
            .claim("email", user.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(key)
            .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public Claims extractSubject(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token).getBody();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = extractSubject(token);
        String email = claims.get("email", String.class);

        return new UsernamePasswordAuthenticationToken(
            email,
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
}
}
