package com.meridian.application;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.meridian.api.*"})
@EntityScan(basePackages = {"com.meridian.api.*"})
@EnableJpaRepositories(basePackages = {"com.meridian.api.*"})
public class MeridianApplication {

    public static void main(String... args) {
        SpringApplication.run(MeridianApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}