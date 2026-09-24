package com.medicare.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(
            UserDetails userDetails) {

        long currentTime =
                System.currentTimeMillis();

        return Jwts.builder()
                .subject(
                        userDetails.getUsername()
                )
                .issuedAt(
                        new Date(currentTime)
                )
                .expiration(
                        new Date(
                                currentTime
                                        + jwtExpiration
                        )
                )
                .signWith(
                        getSigningKey()
                )
                .compact();
    }

    public String extractUsername(
            String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String username =
                extractUsername(token);

        return username.equals(
                userDetails.getUsername()
        )
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(
            String token) {

        Date expiration =
                extractAllClaims(token)
                        .getExpiration();

        return expiration.before(
                new Date()
        );
    }

    private Claims extractAllClaims(
            String token) {

        return Jwts.parser()
                .verifyWith(
                        getSigningKey()
                )
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64URL
                        .decode(jwtSecret);

        return Keys.hmacShaKeyFor(
                keyBytes
        );
    }
}