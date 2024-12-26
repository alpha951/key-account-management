package org.kamsystem.authentication.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.kamsystem.common.configuration.WebappConfig;
import org.kamsystem.common.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final WebappConfig webappConfig;

    public JwtTokenProvider(WebappConfig webappConfig) {
        this.webappConfig = webappConfig;
    }

    public String generateToken(Long userId, UserRole role) {
        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("role", role.name())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + webappConfig.getExpirationTime()))
            .signWith(getSecretKey(webappConfig.getSecretKey()), SignatureAlgorithm.HS512)
            .compact();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey(webappConfig.getSecretKey()))
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey(webappConfig.getSecretKey()))
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey(webappConfig.getSecretKey()))
                .build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private SecretKey getSecretKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}

