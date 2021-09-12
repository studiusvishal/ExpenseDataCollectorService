package com.bhavsar.vishal.service.datacollector.events;

import com.bhavsar.vishal.service.datacollector.model.login.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final User user;
    private final Locale locale;
    private final String appUrl;

    public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
