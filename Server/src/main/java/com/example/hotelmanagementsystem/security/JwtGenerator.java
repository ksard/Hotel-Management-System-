package com.example.hotelmanagementsystem.security;
import java.util.Date;

import com.example.hotelmanagementsystem.models.Configuration;
import com.example.hotelmanagementsystem.models.SharedData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    @Autowired
    private Configuration configuration;
    public JwtGenerator(Configuration configuration)
    {
        this.configuration=configuration;

    }
    public String generateToken(Authentication authentication, Long personId) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + configuration.getExpiryTime());
        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", authentication.getAuthorities())
                .claim("personId", personId)
                .setIssuedAt( new Date())
                .setExpiration(expireDate)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
        return token;
    }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException(SharedData._JWTExpired,ex.fillInStackTrace());
        }
    }

}