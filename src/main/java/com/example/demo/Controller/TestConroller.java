package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.List;


@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.demo"}) // com.my.jpa.repository 하위에 있는 jpaRepository를 상속한 repository scan
@EntityScan(basePackages = {"com.example.demo"}) // com.my.jpa.entity 하위에 있는 @Entity 클래스 scan
@RestController
public class TestConroller {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/test")
    public List<User> test() {
        return userRepository.search();
    }

    @RequestMapping("/")
    public String home() {
        return "Hello World!";

    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(TestConroller.class, args);
/*
        String url = "jdbc:mysql://localhost:3306/blog?serverTimezone=UTC&characterEncoding=UTF-8";
        String userName = "root";
        String password = "admin";

        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from blogcontent");

        resultSet.next();
        String name = resultSet.getString("title");
        System.out.println(name);

        resultSet.close();
        statement.close();
        connection.close();

 */
    }






}