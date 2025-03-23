package com.e_commerce_tecprime_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement; 

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class ECommerceTecprimeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceTecprimeServiceApplication.class, args);
    }
}
