package com.bhavsar.vishal.service.datacollector.events.listeners;

import com.bhavsar.vishal.service.datacollector.events.OnRegistrationCompleteEvent;
import com.bhavsar.vishal.service.datacollector.model.login.User;
import com.bhavsar.vishal.service.datacollector.security.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Log4j2
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.sendRegistrationEmail(event);
    }

    private void sendRegistrationEmail(final OnRegistrationCompleteEvent event) {
        log.debug("inside confirm registration...");
        final var user = event.getUser();
        final var token = UUID.randomUUID().toString();
        userService.generateVerificationTokenForUser(user, token);
        final var email = constructEmailMessage(event, user, token);
        log.debug("Confirmation link: {}", email.getText());
        mailSender.send(email);
        log.info("Confirmation email sent successfully...");
    }

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event,
                                                    final User user, final String token) {
        final var recipientAddress = user.getEmail();
        final var locale = event.getLocale();
        final var subject = messages.getMessage("registration.email.subject", null, locale);
        final var confirmationUrl = event.getAppUrl() + "/confirmRegistration?token=" + token;
        final var message = messages.getMessage("registration.email.body", null, locale);
        final var email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(Objects.requireNonNull(env.getProperty("support.email")));
        return email;
    }
}
