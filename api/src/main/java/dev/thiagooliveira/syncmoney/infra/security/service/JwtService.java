package dev.thiagooliveira.syncmoney.infra.security.service;

import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final byte[] secretKey;

  public JwtService(@Value("${app.jwt.secret}") String secret) { // TODO
    this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
  }

  public String generateToken(UserWithPassword user) {
    return Jwts.builder()
        .setSubject(user.getEmail())
        .claim("role", "admin")
        .claim("userId", user.getId())
        .claim("organizationId", user.getOrganizationId())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    return extractUsername(token).equals(userDetails.getUsername());
  }
}
