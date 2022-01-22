package com.joongoprime.backend.jpatest;

import com.joongoprime.backend.entity.Users;
import com.joongoprime.backend.entity.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JpaTest {

    @Autowired
    UsersRepository usersRepository;

    @Test
    void save() {
        Users users = Users.builder()
                .uid("test@gmail.com")
                .user_name("names")
                .password("dfljielw")
                .account_type("KAKAO")
                .points(100)
                .build();
        usersRepository.save(users);

    }
}
