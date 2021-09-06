package com.bhavsar.vishal.service.datacollector.controller;

import com.bhavsar.vishal.service.datacollector.model.login.User;
import com.bhavsar.vishal.service.datacollector.model.login.UserSignUpResponse;
import com.bhavsar.vishal.service.datacollector.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUp(@RequestBody final User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            val userResponse = userRepository.save(user);
            final UserSignUpResponse signUpResponse = UserSignUpResponse.builder()
                    .message("User registration successful.")
                    .user(userResponse)
                    .build();
            return ResponseEntity.ok(signUpResponse);
        } catch (final DataIntegrityViolationException e) {
            val msg = String.format("Username '%s' already exists.", user.getUsername());
            throw new DataIntegrityViolationException(msg);
        }
    }
}
