package com.bhavsar.vishal.service.datacollector.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponse {
    private String name;
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private boolean enabled;
    private boolean isUsing2FA;
}
