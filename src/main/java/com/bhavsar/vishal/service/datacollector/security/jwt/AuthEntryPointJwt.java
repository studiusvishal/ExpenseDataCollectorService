package com.bhavsar.vishal.service.datacollector.security.jwt;

import com.bhavsar.vishal.service.datacollector.exceptions.model.ApiError;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@Log4j2
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage(), authException);
        final var errors = new ArrayList<>();
        errors.add(authException.getLocalizedMessage());
        final var apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Unauthorized")
                .errors(errors)
                .build();
        final var errorResponseString = new Gson().toJson(apiError);
        final var out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        out.print(errorResponseString);
        out.flush();
    }
}
