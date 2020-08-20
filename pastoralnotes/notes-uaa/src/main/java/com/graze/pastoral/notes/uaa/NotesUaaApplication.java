package com.graze.pastoral.notes.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableResourceServer
@SpringBootApplication
@EntityScan("com.graze.pastoral.notes.domain.entity")
public class NotesUaaApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotesUaaApplication.class, args);
    }
}
