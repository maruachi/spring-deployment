package com.example.springdeployment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
    @GetMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "hello world";
    }

    @GetMapping("/new")
    @ResponseBody
    public String newHelloWorld() {
        return "new hello world feature added";
    }
}
