package com.bhavsar.vishal.service.datacollector.security.services;

import com.bhavsar.vishal.service.datacollector.model.login.User;

public interface TokenService {
    boolean isCodeValid(String username, String code);
    boolean isCodeValid(User user, String code);
    void delete(String token);
}
