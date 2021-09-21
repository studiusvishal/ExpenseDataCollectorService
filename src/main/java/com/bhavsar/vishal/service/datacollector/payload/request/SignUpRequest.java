package com.bhavsar.vishal.service.datacollector.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("fullName")
    private String name;

    @NotBlank
    @Size(min = 3, max = 20, message = "Username '${validatedValue}' must be between {min} to {max} characters long.")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private boolean using2FA;
    private Set<String> roles;
}
