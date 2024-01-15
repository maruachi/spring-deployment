package com.example.springdeployment.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class Person {
    private Integer id;
    private String userName;
    private String email;
    private LocalDateTime datetime;

    public Person(Integer id, String userName, String email, LocalDateTime datetime) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.datetime = datetime;
    }
}
