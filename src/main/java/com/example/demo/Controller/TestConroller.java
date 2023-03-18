package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.demo"}) // com.my.jpa.repository 하위에 있는 jpaRepository를 상속한 repository scan
@EntityScan(basePackages = {"com.example.demo"}) // com.my.jpa.entity 하위에 있는 @Entity 클래스 scan
@RestController
public class TestConroller {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postsRepository;

    @GetMapping("/test")
    public List<User> test() {

        List<User> cc = userRepository.search();
        System.out.println(" ttt :: "+cc.size());
        System.out.println(" ttt :: "+cc.get(0).getUsername());
        return cc;
    }

    @GetMapping("/postList")
    public  DataModel[] postList() {
        List<PostsModel> postList = postsRepository.findAll();
        System.out.println(" ttt :: "+postList.size());
        int size = postList.size()/3;
        DataModel[] models = new DataModel[3];
        int temp = 1;
        int temp2 = 0;

        models[0] = new DataModel();
        models[1] = new DataModel();
        models[2] = new DataModel();

        for(int i=0; i<3; i++)
        {
            if(i == 2){
                models[temp2].Id = temp;
                temp2++;
            }

            if(i/postList.size() == 0){

            }


            models[temp2].Hot.add(postList.get(i));
        }

        return models;
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