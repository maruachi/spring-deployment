package com.example.springdeployment.controller;

import com.example.springdeployment.domain.Person;
import com.example.springdeployment.domain.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PersonController {
    private final PersonRepository personRepository;

    @GetMapping("/user")
    @ResponseBody
    public String searchUser() {
        List<Person> people = personRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : people) {
            String string = person.toString();
            stringBuilder.append(string).append("<br>");
        }
        return stringBuilder.toString();
    }
}
