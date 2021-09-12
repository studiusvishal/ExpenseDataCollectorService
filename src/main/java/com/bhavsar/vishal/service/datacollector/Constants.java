package com.bhavsar.vishal.service.datacollector;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    // 1 hour = 60 minutes = 60 × 60 seconds = 3600 seconds = 3600 × 1000 milliseconds = 3,600,000 ms.
    public static final long PURGE_TOKEN_SCHEDULER_DELAY = 60 * 60 * 1000;

    public static final String PROFILE_LOCAL = "local";
    public static final String PROFILE_DEV = "dev";
}