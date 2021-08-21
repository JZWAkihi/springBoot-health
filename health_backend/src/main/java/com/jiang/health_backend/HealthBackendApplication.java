package com.jiang.health_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class HealthBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthBackendApplication.class, args);
    }

}
