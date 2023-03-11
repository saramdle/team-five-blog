package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//RestController 어노테이션 추가
@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	//localhost:8080를 호출하면 helloWorld를 찍어주기 위한 함수
	@RequestMapping("/")
	public String helloWorld(){
		return "helloWorld";
	}

}