package com.graze.pastoral.notes.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/17 16:50
 */

@EnableEurekaServer
@SpringBootApplication
public class NotesEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotesEurekaApplication.class, args);
    }
}
