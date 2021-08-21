package com.jiang.health_service_provider;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.jiang.health_service_provider.dao")
public class HealthServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthServiceProviderApplication.class, args);
    }

}
