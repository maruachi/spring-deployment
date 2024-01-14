package com.example.springdeployment.controller;

import com.example.springdeployment.domain.User;
import com.example.springdeployment.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/user")
    @ResponseBody
    public String searchUser() {
        List<User> users = userRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            String string = user.toString();
            stringBuilder.append(string).append("<br>");
        }
        return stringBuilder.toString();
    }
}
