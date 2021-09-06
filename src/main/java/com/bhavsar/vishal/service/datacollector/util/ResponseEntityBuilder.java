package com.bhavsar.vishal.service.datacollector.util;

import com.bhavsar.vishal.service.datacollector.exceptions.model.ApiError;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
    public static ResponseEntity<Object> build(final ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
