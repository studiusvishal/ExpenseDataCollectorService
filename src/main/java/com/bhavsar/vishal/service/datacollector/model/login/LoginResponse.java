package com.bhavsar.vishal.service.datacollector.model.login;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String username;
    private String authToken;
    private boolean isLoginSuccessful;
    private int status;
}
