package com.garagebenz.demo.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    private static final String SECRET_KEY = "Micl@veMuuysecretaylargaY$EguR@11122!!a";

    public String generateToken(UserDetails userDetails, String id, String rol) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", id);
        extraClaims.put("rol", rol);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    // Añade métodos para extractUsername e isTokenValid
}
