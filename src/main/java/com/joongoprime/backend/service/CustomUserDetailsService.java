package com.joongoprime.backend.service;


import com.joongoprime.backend.entity.Users;
import com.joongoprime.backend.entity.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return restMapper.findByEmail(username)
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        Optional<Users> user = usersRepository.findById(username);

        if (!user.isPresent()){
            throw new UsernameNotFoundException("없는 유저입니다");
        }

        Users myUser = user.get();


        return myUser;
    }
    //---- 아래 함수에서는 UserObject가 일반 생성자로만 들어감. (DB를 JPA로 다루지도 않고, Entity도 아니다 보니까 차이가 있었던 듯.
    // ---- 그런데 UserObject 클래스의 경우 UserDetail을 구현한 클래스기 때문에 그대로 리턴해도 상관이 없음. 너무 꼬아서 생각했음

//    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
//    private UserDetails createUserDetails(UserObject users) {
//        return new UserObject(users.getUsername(), users.getPassword(), users.getAuthorities());
//    }
}