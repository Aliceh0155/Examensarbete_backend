package com.alice.examensarbete_backend.config.security.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

  private final SecretKey key;

  public JwtUtil(@Value("${jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
  }

  // JWT expiration time 1 hour
  private final int jwtExpirationMs = (int) TimeUnit.HOURS.toMillis(1);

  public String generateJwtToken(String username, String role) {
    return Jwts.builder()
            .subject(username)
            .claim("role", role)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(key)
            .compact();
  }

  public String getUsernameFromJwtToken(String token) {
    Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

    return claims.getSubject();
  }

  public String getRoleFromJwtToken(String token) {
    Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();


    return claims.get("role", String.class);
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser()
              .verifyWith(key)
              .build()
              .parseSignedClaims(authToken);
      return true;
    } catch (Exception e) {
      System.err.println("Invalid JWT token: " + e.getMessage());
    }
    return false;
  }
}
