package com.george.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.george.springframework.api.v1", "com.george.springframework"})
public class Spring5MvcRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring5MvcRestApplication.class, args);
    }
}
