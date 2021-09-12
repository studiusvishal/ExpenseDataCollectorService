package com.bhavsar.vishal.service.datacollector.security.services.impl;

import com.bhavsar.vishal.service.datacollector.model.login.User;
import com.bhavsar.vishal.service.datacollector.repository.TokenRepository;
import com.bhavsar.vishal.service.datacollector.repository.UserRepository;
import com.bhavsar.vishal.service.datacollector.security.services.TokenService;
import lombok.extern.log4j.Log4j2;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Value("${app.debug.mode:false}")
    private boolean isDebugModeOn;

    @Override
    public boolean isCodeValid(final String username, final String code) {
        final var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return isCodeValid(user, code);
    }

    @Override
    public boolean isCodeValid(final User user, final String code) {
        log.info("Verifying OTP code for user...");
        if (isDebugModeOn && code.equals("1")) {
            return true;
        }

        final var totp = new Totp(user.getSecret());
        if (!isValidLong(code) || !totp.verify(code)) {
            log.error("Not valid OTP code.");
            return false;
        }
        return true;
    }

    @Override
    public void delete(final String token) {
        log.info("Deleting token after successful registration verification...");
        tokenRepository.delete(token);
    }

    public boolean isValidLong(final String input) {
        try {
            Long.parseLong(input);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
