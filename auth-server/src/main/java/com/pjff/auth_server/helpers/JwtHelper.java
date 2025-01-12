package com.pjff.auth_server.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

//Vid 105
@Component
@Slf4j
public class JwtHelper {

    // Vid 109
    @Value("${application.jwt.secret}")
    private String jwtSecret;

    // Vid 106
    public String createToken(String username) {
        final var now = new Date();
        final var expirationDate = new Date(now.getTime() + (3600 * 1000));
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(this.getSecretKey())
                .compact();
    }

    // Vid 106
    public boolean validateToken(String token) {
        // Vid 103
        try {
            final var expirationDate = this.getExpirationDate(token);
            // Ver que no haya caducado
            return expirationDate.after(new Date());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Jwt invalid");
        }

    }

    // Vid 105,ver cuando el token no sea caducado
    private Date getExpirationDate(String token) {
        return this.getClaimsFromToken(token, Claims::getExpiration);
    }

    // Vid 105
    private <T> T getClaimsFromToken(String token, Function<Claims, T> resolver) {
        return resolver.apply(this.signToken(token));
    }

    // Vid 105 ,Para firmar el token
    private Claims signToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Vid 105
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
