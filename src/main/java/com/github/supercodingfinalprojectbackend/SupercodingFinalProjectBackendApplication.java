package com.github.supercodingfinalprojectbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@SpringBootApplication
public class SupercodingFinalProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupercodingFinalProjectBackendApplication.class, args);
    }

}
