DROP TABLE IF EXISTS MEMBER;

CREATE TABLE PERSON
(
    id    INT PRIMARY KEY,
    user_name   VARCHAR(50),
    email      VARCHAR(100),
    datetime DATETIME
);