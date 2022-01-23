package com.joongoprime.backend.jpatest;

import com.joongoprime.backend.entity.Users;
import com.joongoprime.backend.entity.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class JpaTest {

    @Autowired
    UsersRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger("로그 출력 도구");

    @Test
    void save() {
        Users users = Users.builder()
                .uid("test1@gmail.com")
                .user_name("names")
                .password("dfljielw")
                .account_type("KAKAO")
                .points(100)
                .build();
        usersRepository.save(users);
//        Optional<Users> temp;
//        temp = usersRepository.findById("test2@gmail.com");
//        if (temp.isPresent()) {
//            Users user = temp.get();
//            logger.info(user.getUid());
//        }
    }
}
