package com.bhavsar.vishal.service.datacollector.util;

import com.bhavsar.vishal.service.datacollector.model.login.ERole;
import com.bhavsar.vishal.service.datacollector.model.login.Role;
import com.bhavsar.vishal.service.datacollector.model.login.User;
import com.bhavsar.vishal.service.datacollector.payload.request.SignUpRequest;
import com.bhavsar.vishal.service.datacollector.security.services.RoleService;
import lombok.extern.log4j.Log4j2;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@Component
public class AuthUtils {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public User createUser(final SignUpRequest signUpRequest) {
        return User.builder()
                .name(signUpRequest.getName())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .using2FA(signUpRequest.isUsing2FA())
                .roles(getRoles(signUpRequest.getRoles()))
                .secret(Base32.random())
                .enabled(false) // Keep user disabled during sign up. Send email for confirmation and then enable user.
                .build();
    }

    private Set<Role> getRoles(final Set<String> strRoles) {
        final Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            final Role userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles
                    .stream()
                    .map(stringRole -> roleService.findByName(ERole.fromString(stringRole))
                            .orElseThrow(() -> new RuntimeException(String.format("Error: Role '%s' is not found.", stringRole))))
                    .forEach(roles::add);
        }
        return roles;
    }
}
