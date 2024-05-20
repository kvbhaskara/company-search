package com.company.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.company.search")
public class CompanySearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanySearchApplication.class, args);
    }

}
