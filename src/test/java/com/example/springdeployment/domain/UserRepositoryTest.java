package com.example.springdeployment.domain;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void test1() {
        System.out.println("hello world");
    }

    @Test
    void test2() {
        System.out.println(userRepository);
        List<User> users = userRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            String string = user.toString();
            stringBuilder.append(string).append('\n');
        }
        System.out.println(stringBuilder);
    }
}