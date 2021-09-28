package com.bhavsar.vishal.service.datacollector.security.services;

import com.bhavsar.vishal.service.datacollector.db.entity.user.User;

public interface TokenService {
    boolean isCodeValid(String username, String code);
    boolean isCodeValid(User user, String code);
    void delete(String token);
}
