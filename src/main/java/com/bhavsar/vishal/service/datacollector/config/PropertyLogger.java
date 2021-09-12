package com.bhavsar.vishal.service.datacollector.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import static com.bhavsar.vishal.service.datacollector.Constants.PROFILE_DEV;
import static com.bhavsar.vishal.service.datacollector.Constants.PROFILE_LOCAL;

@Log4j2
@Component
@Profile(value = {PROFILE_LOCAL, PROFILE_DEV})
public class PropertyLogger {
    @Autowired
    private Environment env;

    @EventListener
    public void handleContextRefresh(final ContextRefreshedEvent event) {
        // final Environment env = event.getApplicationContext().getEnvironment();
        log.info("====== Environment and configuration ======");
        log.info("Active profiles: {}", Arrays.toString(env.getActiveProfiles()));
        final var sources = ((AbstractEnvironment) env).getPropertySources();
        StreamSupport.stream(sources.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .sorted()
                .forEach(prop -> log.info("{}: {}", prop, env.getProperty(prop)));
        log.info("===========================================");
    }
}
