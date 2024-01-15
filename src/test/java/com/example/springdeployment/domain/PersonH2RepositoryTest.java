package com.example.springdeployment.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class PersonH2RepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void test1() {
        System.out.println("hello world");
    }

    @Test
    void test2() {
        System.out.println(personRepository);
        List<Person> people = personRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : people) {
            String string = person.toString();
            stringBuilder.append(string).append('\n');
        }
        System.out.println(stringBuilder);
    }
}