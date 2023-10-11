package com.soloSavings.config;

import com.soloSavings.model.TokenDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    private static final byte[] SECRET = "694a28b26c854d7eaf1bd2c72aef58acc216edee0fc845a882b0aba8b545df69".getBytes(StandardCharsets.UTF_8);
    private static final Long EXPIRATION = (long) (30 * 60 * 1000); // 30 minutes

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // TODO(will): Use non-deprecated functions...
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public TokenDetails generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        Date expiry = new Date(System.currentTimeMillis() + EXPIRATION);
        String token = createToken(claims, username, expiry);
        return new TokenDetails(token, expiry);
    }

    private String createToken(Map<String, Object> claims, String subject, Date expiry) {
        // TODO(will): Use non-deprecated functions
        SecretKey key = Keys.hmacShaKeyFor(SECRET);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate a JWT token
    public boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().getExpiration();
    }
}
