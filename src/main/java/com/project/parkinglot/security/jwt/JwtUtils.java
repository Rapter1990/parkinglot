package com.project.parkinglot.security.jwt;

import com.project.parkinglot.security.CustomUserDetails;
import com.project.parkinglot.security.model.enums.TokenClaims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Utility class named {@link JwtUtils} for JWT operations.
 */
@Component
@Log4j2
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expireMs}")
    private int jwtExpirationMs;

    /**
     * Generates JWT token.
     *
     * @param auth The authentication object.
     * @return Generated JWT token.
     */
    public String generateJwtToken(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Map<String, Object> claims = userDetails.getClaims();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates JWT token.
     *
     * @param claims  The claims to be included in the token.
     * @param subject The subject of the token.
     * @return Generated JWT token.
     */
    public String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates JWT token for a custom user.
     *
     * @param customUserDetails The custom user details.
     * @return Generated JWT token.
     */
    public String generateJwtToken(CustomUserDetails customUserDetails) {
        Map<String, Object> claims = customUserDetails.getClaims();
        claims.put(TokenClaims.ID.getValue(), customUserDetails.getId());
        return createToken(claims, customUserDetails.getUsername());
    }

    /**
     * Retrieves the signing key from the JWT secret.
     *
     * @return The signing key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts claims from JWT token.
     *
     * @param token The JWT token.
     * @return Claims extracted from the token.
     */
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts user ID from JWT token.
     *
     * @param token The JWT token.
     * @return Extracted user ID.
     */
    public String getIdFromToken(String token) {
        String idValue = extractClaims(token).get(TokenClaims.ID.getValue()).toString();
        return idValue;
    }

    /**
     * Extracts user email from JWT token.
     *
     * @param token The JWT token.
     * @return Extracted user email.
     */
    public String getEmailFromToken(String token) {
        return extractClaims(token).get(TokenClaims.EMAIL.getValue()).toString();
    }

    /**
     * Validates JWT token.
     *
     * @param authToken The JWT token.
     * @return True if the token is valid, otherwise false.
     */
    public boolean validateJwtToken(String authToken) {

        log.info("JwtUtils | validateJwtToken | authToken: {}", authToken);

        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("JwtUtils | validateJwtToken | Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JwtUtils | validateJwtToken | JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JwtUtils | validateJwtToken | JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JwtUtils | validateJwtToken | JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Extracts JWT token from the authorization header.
     *
     * @param authorizationHeader The authorization header.
     * @return Extracted JWT token.
     */
    public String extractTokenFromHeader(String authorizationHeader) {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
