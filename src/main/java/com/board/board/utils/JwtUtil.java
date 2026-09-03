package com.board.board.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  private final String secret = "y61cSzMSzHzoGxOBcuWQzIxPOe2348ZGNoo76JclyOx-this-is-secret-key"; 

  private SecretKey getSigningKey(){
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generateJwtToke(String email, long expirationMillis){
    return Jwts.builder()
                  .subject(email)
                  .issuedAt(new Date())
                  .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                  .signWith(getSigningKey())
                  .compact();
  }

  public String extractEmail(String token) {
    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
  }

  public boolean validateToken(String token) {
    try {
        Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
        return true;
    } catch (Exception e) {
        return false;
    }
  }
}
