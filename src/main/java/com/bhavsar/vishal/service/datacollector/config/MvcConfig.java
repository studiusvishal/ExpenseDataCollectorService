package com.bhavsar.vishal.service.datacollector.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class MvcConfig {
//    @Bean
//    public AuthenticationManager customAuthenticationManager() throws Exception {
//        log.info("Initializing custom authentication manager...");
//        return authenticationManager();
//    }

    @Bean(name = "bcryptPasswordEncoder")
    public PasswordEncoder bCryptPasswordEncoder() {
        log.info("Initializing BCryptPasswordEncoder...");
        return new BCryptPasswordEncoder();
    }
}
