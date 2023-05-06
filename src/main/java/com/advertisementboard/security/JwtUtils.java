package com.advertisementboard.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${advertisement-board.authorization.secret}")
    private String secret;

    @Value("${advertisement-board.authorization.expiration}")
    private int expiration;

    public String generateJwtToken(String login){
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
