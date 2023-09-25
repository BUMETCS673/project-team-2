package com.soloSavings.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private static Key secret;

    @Value("${jwt.expiration}")
    private static Long expiration;

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    // Validate a JWT token
    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        // Split the token into header, payload, and signature
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false;
        }

        String header = parts[0];
        String payload = parts[1];
        String signature = parts[2];

        // Verify the signature - TODO Need correct algorithm
//        String expectedSignature = ??? ;
//        if (!signature.equals(expectedSignature)) {
//            return false;
//        }

        // Decode the payload to check the expiration date
        String decodedPayload = decodeBase64(payload);
        try {
            long expirationTime = Long.parseLong(decodedPayload.split("\"exp\":")[1].split(",")[0]);
            if (expirationTime < new Date().getTime()) {
                return false; // Token is expired
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false; // Invalid payload format
        }

        return true;
    }

    private String decodeBase64(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody().getExpiration();
    }

    private String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
    }

    private long getExpirationDate() {
        return new Date().getTime() + 3600 * 1000; // 1 hour in milliseconds
    }
}
