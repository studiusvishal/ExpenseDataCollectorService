package com.bhavsar.vishal.service.datacollector.security.services.impl;

import com.bhavsar.vishal.service.datacollector.model.login.TokenValidityStatus;
import com.bhavsar.vishal.service.datacollector.model.login.User;
import com.bhavsar.vishal.service.datacollector.model.login.mfa.VerificationToken;
import com.bhavsar.vishal.service.datacollector.repository.TokenRepository;
import com.bhavsar.vishal.service.datacollector.repository.UserRepository;
import com.bhavsar.vishal.service.datacollector.security.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.bhavsar.vishal.service.datacollector.model.login.TokenValidityStatus.*;

@Log4j2
@Service
public class UserDetailsServiceImpl implements UserService {
    @Value("${money.tracker.service.qr_prefix}")
    private String qrPrefix;

    @Value("${money.tracker.service.name:MoneyTrackerService}")
    private String appName;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    @Override
    public void generateVerificationTokenForUser(final User user, final String token) {
        log.info("creating verification token for user");
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(final User user) {
        userRepository.save(user);
    }

    @Override
    public TokenValidityStatus validateVerificationToken(final String token) {
        log.info("Validating verification token...");
        final var verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            log.error("No record found in token repository for token '{}'.", token);
            return TOKEN_INVALID;
        }
        if (verificationToken.isExpired()) {
            log.warn("Verification token is expired. Deleting from repository.");
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }
//         var user = verificationToken.getUser();
//         user.setEnabled(true);
//         tokenRepository.delete(verificationToken);
//         userRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public User getUserFromVerificationToken(final String verificationToken) {
        log.info("Getting user from verification token");
        final var token = tokenRepository.findByToken(verificationToken);
        if (token == null) {
            log.error("No record found in token repository for token '{}'. Cannot find user.", verificationToken);
            return null;
        }
        log.debug("Returning user: {}", token.getUser().getId());
        return token.getUser();
    }

    @Override
    public void enableUser(final String username) {
        log.info("Enabling user {}", username);
        userRepository.enableUser(username);
    }

    @Override
    public String generateQRUrl(final User user) {
        final String urlFormat = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
                appName, user.getUsername(), user.getSecret(), appName);
        return qrPrefix + URLEncoder.encode(urlFormat, StandardCharsets.UTF_8);
    }
}
