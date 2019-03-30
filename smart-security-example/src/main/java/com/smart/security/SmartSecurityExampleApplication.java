package com.smart.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SmartSecurityExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSecurityExampleApplication.class, args);
    }

    @GetMapping("get")
    public String get(){
        return "init success";
    }
}
