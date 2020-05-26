package com;

import com.config.WebConfig;
import setup.JPAConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.controller", "com.session", "service", "model", "repository"})
@EntityScan("model")
@Import({JPAConfig.class, WebConfig.class})
public class CMServer {

    public static void main(String[] args) {
        SpringApplication.run(CMServer.class, args);
    }

}
