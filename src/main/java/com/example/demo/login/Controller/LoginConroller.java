package com.example.demo.login.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class LoginConroller {

    //@Autowired
    //private PostRepository postsRepository; //로그인용으로 만들어야함

    @RequestMapping(value = "/login/createAccount", method = RequestMethod.POST)
    public String CreateAccount() {
        return "계정을 만든다..";
    }

}