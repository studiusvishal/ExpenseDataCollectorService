package com.bhavsar.vishal.service.datacollector.security.jwt;

import com.bhavsar.vishal.service.datacollector.payload.response.auth.LoginResponse;
import com.bhavsar.vishal.service.datacollector.security.services.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtUtils {
    @Value("${money.tracker.service.jwtSecret}")
    private String jwtSecret;

    @Value("${money.tracker.service.jwtExpirationMs}")
    private int jwtExpirationMs;

    private String generateJwtToken(final Authentication authentication) {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(final String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(final String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (final SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (final MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (final ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (final UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (final IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public LoginResponse createResponse(final Authentication authentication) {
        final var authToken = generateJwtToken(authentication);
        final var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final var roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return LoginResponse.builder()
                .name(userDetails.getName())
                .enabled(userDetails.isEnabled())
                .token(authToken)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }
}
