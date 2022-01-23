package com.joongoprime.backend.service;

import com.joongoprime.backend.entity.Users;
import com.joongoprime.backend.entity.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public Users create(Users user){
        return usersRepository.save(user);
    }

    public Optional<Users> read(String uid) {
        return usersRepository.findById(uid);
    }
}
