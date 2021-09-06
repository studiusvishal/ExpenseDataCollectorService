package com.bhavsar.vishal.service.datacollector.model.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpResponse {
    private User user;
    private String message;
}
