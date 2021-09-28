package com.bhavsar.vishal.service.datacollector.model.login;

import lombok.NonNull;

public enum ERole {
    ROLE_USER("user"),
    ROLE_MODERATOR("mod"),
    ROLE_ADMIN("admin");

    private final String name;

    ERole(final String name) {
        this.name = name;
    }

    public static ERole fromString(@NonNull final String stringRole) {
        for (final ERole erole : ERole.values()) {
            if (erole.name.equalsIgnoreCase(stringRole)) {
                return erole;
            }
        }
        return ERole.ROLE_USER;
    }
}
