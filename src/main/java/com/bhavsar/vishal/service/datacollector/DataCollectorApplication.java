package com.bhavsar.vishal.service.datacollector;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.bhavsar.vishal.service.datacollector")
@Log4j2
public class DataCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCollectorApplication.class, args);
    }

}
