package com.jiang.healthmobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class HealthmobileApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthmobileApplication.class, args);
    }

}
