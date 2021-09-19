package com.bhavsar.vishal.service.datacollector.controller;

import com.bhavsar.vishal.service.datacollector.events.OnRegistrationCompleteEvent;
import com.bhavsar.vishal.service.datacollector.payload.request.LoginRequest;
import com.bhavsar.vishal.service.datacollector.payload.request.SignUpRequest;
import com.bhavsar.vishal.service.datacollector.payload.response.MessageResponse;
import com.bhavsar.vishal.service.datacollector.security.jwt.JwtUtils;
import com.bhavsar.vishal.service.datacollector.security.services.TokenService;
import com.bhavsar.vishal.service.datacollector.security.services.UserService;
import com.bhavsar.vishal.service.datacollector.util.AuthUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messages;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthUtils authUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody final LoginRequest loginRequest) {
        final var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        final var authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var loginResponse = jwtUtils.createResponse(authentication);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody final SignUpRequest signUpRequest,
                                          final HttpServletRequest request) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        final var user = authUtils.createUser(signUpRequest);
        userService.save(user);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
        final String msg = messages.getMessage("message.registration.success", null, request.getLocale());
        return ResponseEntity.ok(new MessageResponse(msg));
    }

    @GetMapping("/confirmRegistration")
    public MessageResponse confirmRegistration(final HttpServletRequest request,
                                               final RedirectAttributes redirectAttributes,
                                               @RequestParam("token") final String token) {
        final var locale = request.getLocale();
        // validate the token which was generated during registration and sent in the email
        final var tokenValidityStatus = userService.validateVerificationToken(token);
        final var messageResponseBuilder = MessageResponse.builder();
        switch (tokenValidityStatus) {
            case TOKEN_VALID:
                final var user = userService.getUserFromVerificationToken(token);
                userService.enableUser(user.getUsername());
                tokenService.delete(token);
                messageResponseBuilder.message("User verification completed. User is enabled. You can proceed to login in the app.");
// if user has not opted for OTP, then once he clicks on email, activate his account.
//                TODO: Revisit when working on MFA
//                if (user.isUsing2FA()) {
//                    String qrUrl = userService.generateQRUrl(user);
//                    log.debug("QR URL: {}", qrUrl);
//                    redirectAttributes.addAttribute("qr_code", qrUrl);
//                    redirectAttributes.addAttribute("user", user.getUsername());
//                    redirectUrl = "redirect:/qrcode";
//                } else {
//                    // if user has not opted for OTP, then once he clicks on email, activate his account.
//                    userService.enableUser(user.getUsername());
//                    return "redirect:/registrationComplete";
//                }
                break;
            case TOKEN_EXPIRED:
            case TOKEN_INVALID:
//                redirectAttributes.addAttribute("message", messages.getMessage("auth.message.authtoken." + tokenValidityStatus.getTokenStatus(), null, locale));
//                redirectAttributes.addAttribute("expired", tokenValidityStatus);
//                redirectAttributes.addAttribute("token", token);
                messageResponseBuilder.message("User verification failed. Token is " + tokenValidityStatus.getTokenStatus());
                // TODO: Add feature to resend verification token email.
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tokenValidityStatus);
        }
        return messageResponseBuilder.build();
    }

    private String getAppUrl(final HttpServletRequest request) {
        return String.format("http://%s:%s/api/auth%s", request.getServerName(), request.getServerPort(), request.getContextPath());
    }
}
