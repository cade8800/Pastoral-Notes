package com.graze.pastoral.notes.api;

import com.graze.pastoral.notes.api.config.FileProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/17 17:32
 */

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableEurekaClient
@EnableTransactionManagement
@EntityScan({"com.graze.pastoral.notes.domain"})
@EnableConfigurationProperties({
        FileProperties.class
})
@MapperScan("com.graze.pastoral.notes.api.dao")
public class NotesApiApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        // TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    public static void main(String[] args) {
        SpringApplication.run(NotesApiApplication.class, args);
    }
}
