package com.bhavsar.vishal.service.datacollector.model.login;

import lombok.Getter;

public enum TokenValidityStatus {
    TOKEN_INVALID("invalid"),
    TOKEN_EXPIRED("expired"),
    TOKEN_VALID("valid");

    @Getter
    private final String tokenStatus;

    TokenValidityStatus(final String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }
}
