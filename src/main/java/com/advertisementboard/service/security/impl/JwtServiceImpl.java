package com.advertisementboard.service.security.impl;

import com.advertisementboard.service.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${advertisement-board.authorization.secret}")
    private String secret;

    @Value("${advertisement-board.authorization.expiration}")
    private Long expiration;

    @Override
    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractClaims(token));
    }

    @Override
    public String extractLogin(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(final UserDetails userDetails) {
        return generateToken(userDetails, Map.of());
    }

    @Override
    public String generateToken(final UserDetails userDetails, final Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(final String token, final UserDetails userDetails) {
        return userDetails.getUsername().equals(extractLogin(token)) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
