package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.demo"}) // com.my.jpa.repository 하위에 있는 jpaRepository를 상속한 repository scan
@EntityScan(basePackages = {"com.example.demo"}) // com.my.jpa.entity 하위에 있는 @Entity 클래스 scan
public class SpringApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

}