package com.meridian.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.meridian.api.author"})
@EntityScan(basePackages = {"com.meridian.api.author"})
@EnableJpaRepositories(basePackages = {"com.meridian.api.author"})
public class MeridianApplication {

    public static void main(String... args) {
        SpringApplication.run(MeridianApplication.class, args);
    }
}