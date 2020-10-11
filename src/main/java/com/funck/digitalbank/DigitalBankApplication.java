package com.funck.digitalbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DigitalBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankApplication.class, args);
    }

}
