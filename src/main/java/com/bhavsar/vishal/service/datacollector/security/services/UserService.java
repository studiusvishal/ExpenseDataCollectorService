package com.bhavsar.vishal.service.datacollector.security.services;

import com.bhavsar.vishal.service.datacollector.db.entity.user.User;
import com.bhavsar.vishal.service.datacollector.model.login.TokenValidityStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void generateVerificationTokenForUser(final User user, final String token);

    boolean existsByUsername(final String username);

    boolean existsByEmail(final String email);

    User save(User user);

    Optional<User> findByUsername(String username);

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
