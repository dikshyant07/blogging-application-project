package com.blogging.application.bloggingProject.service;

import com.blogging.application.bloggingProject.exceptions.InvalidJwtException;
import com.blogging.application.bloggingProject.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final Utils utils;

    @Autowired
    public JwtService(Utils utils) {
        this.utils = utils;
    }

    public SecretKey generateKey() {
        byte[] byteStream = Decoders.BASE64.decode(utils.getJwt().getSecret());
        return Keys.hmacShaKeyFor(byteStream);
    }

    public String generateJwt(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 1000L * 60 * utils.getJwt().getExpiry()))
                .claim("Algorithm", "hmacShaKey")
                .signWith(generateKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {

        try {
            return Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new InvalidJwtException("Invalid jwt token provided");
        }
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public boolean isJwtValid(UserDetails userDetails, String token) {
        Claims claims = extractAllClaims(token);
        return !isExpired(token) && claims.getSubject().equals(userDetails.getUsername());
    }
}
