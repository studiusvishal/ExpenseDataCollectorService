package com.bhavsar.vishal.service.datacollector;

import com.bhavsar.vishal.service.datacollector.controller.ExpenseController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
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
