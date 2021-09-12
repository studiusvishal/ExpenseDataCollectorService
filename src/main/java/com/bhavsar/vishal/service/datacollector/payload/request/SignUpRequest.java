package com.bhavsar.vishal.service.datacollector.payload.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class SignUpRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private boolean using2FA;

    @NotBlank
    @Size(min = 3, max = 20, message = "Username '${validatedValue}' must be between {min} to {max} characters long.")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
