package com.bhavsar.vishal.service.datacollector.exceptions;

import com.bhavsar.vishal.service.datacollector.exceptions.model.ApiError;
import com.bhavsar.vishal.service.datacollector.util.ResponseEntityBuilder;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        val details = new ArrayList<>();
        details.add(ex.getMessage());

        val err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Constraint Violations")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(err);
    }
}
