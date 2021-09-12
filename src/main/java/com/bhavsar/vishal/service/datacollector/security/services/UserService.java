package com.bhavsar.vishal.service.datacollector.security.services;

import com.bhavsar.vishal.service.datacollector.model.login.TokenValidityStatus;
import com.bhavsar.vishal.service.datacollector.model.login.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.UnsupportedEncodingException;

public interface UserService extends UserDetailsService {
    void generateVerificationTokenForUser(final User user, final String token);

    boolean existsByUsername(final String username);

    boolean existsByEmail(final String email);

    void save(User user);

    /**
     * Validate the token sent in confirmation email against that in token table.
     * @param token - String
     * @return TokenValidityStatus
     */
    TokenValidityStatus validateVerificationToken(String token);

    User getUserFromVerificationToken(String token);

    void enableUser(String username);

    String generateQRUrl(User user) throws UnsupportedEncodingException;
}
