package com.bhavsar.vishal.service.datacollector.exceptions;

import com.bhavsar.vishal.service.datacollector.exceptions.model.ApiError;
import com.bhavsar.vishal.service.datacollector.util.ResponseEntityBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(final Exception ex, final WebRequest request) {
        final var details = new ArrayList<>();
        details.add(ex.getMessage());

        final var err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Constraint Violations")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(err);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        final var errors = new ArrayList<>();
        for (final var error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final var error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final var apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid value for method arguments.")
                .errors(errors)
                .build();
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler(DisabledException.class)
    protected ResponseEntity<Object> handleDisabledException(
            final HttpHeaders headers,
            final WebRequest request) {
        final var errors = new ArrayList<>();
        final var apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED)
                .message("User is disabled.")
                .errors(errors)
                .build();
        return ResponseEntityBuilder.build(apiError);
    }
}
