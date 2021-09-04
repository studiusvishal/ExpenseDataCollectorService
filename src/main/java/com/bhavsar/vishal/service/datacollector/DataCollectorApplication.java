package com.bhavsar.vishal.service.datacollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.bhavsar.vishal.service.datacollector")
public class DataCollectorApplication {
    public static void main(final String[] args) {
        SpringApplication.run(DataCollectorApplication.class, args);
    }
}
