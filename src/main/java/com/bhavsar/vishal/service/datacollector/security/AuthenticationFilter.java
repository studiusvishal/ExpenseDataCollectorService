package com.bhavsar.vishal.service.datacollector.security;

import com.bhavsar.vishal.service.datacollector.model.login.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

@Log4j2
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    final private AuthenticationManager authenticationManager;

    public AuthenticationFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) throws AuthenticationException {
        try {
            final User creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
                    creds.getPassword(), new ArrayList<>()));
        } catch (final IOException e) {
            throw new RuntimeException("Could not read request" + e);
        }
    }

    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain filterChain,
                                            final Authentication authentication) {
        final String token = Jwts.builder()
                .setSubject(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername())
                .setExpiration(Date.from(ZonedDateTime.now().plusHours(1).toInstant()))
                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
