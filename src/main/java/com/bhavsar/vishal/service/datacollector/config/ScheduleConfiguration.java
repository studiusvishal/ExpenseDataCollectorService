package com.bhavsar.vishal.service.datacollector.config;

import com.bhavsar.vishal.service.datacollector.repository.TokenRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.bhavsar.vishal.service.datacollector.Constants.PURGE_TOKEN_SCHEDULER_DELAY;

@Log4j2
@Configuration
@EnableScheduling
public class ScheduleConfiguration {
    @Autowired
    private TokenRepository tokenRepository;

//    @Value("${money.tracker.service.token.expiry.rate:1}")
//    private int schedularFrequencyInHours;

    // Clear expired tokens every 1hr.
    @Scheduled(fixedDelay = PURGE_TOKEN_SCHEDULER_DELAY)
    @Transactional
    public void purgeExpiredVerifications() {
        final Date currentDateTime = new Date();
        log.info("Deleting all tokens expired/invalid on {}", currentDateTime);
        tokenRepository.deleteAllExpiredSince(currentDateTime);
    }
}
