package com.bhavsar.vishal.service.datacollector.payload.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
