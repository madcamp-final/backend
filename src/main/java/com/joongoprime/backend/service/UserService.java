package com.joongoprime.backend.service;

import com.joongoprime.backend.entity.Users;
import com.joongoprime.backend.entity.UsersRepository;
import com.joongoprime.backend.entity.form.SignIn;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import com.joongoprime.backend.jwt.JwtTokenProvider;
import com.joongoprime.backend.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder; //?
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;


    public Users create(Users user){
        return usersRepository.save(user);
    }

    public Optional<Users> read(String uid) {
        return usersRepository.findById(uid);
    }

    public DefaultResponse userSignIn(@Validated SignIn signInForm){

        UsernamePasswordAuthenticationToken authenticationToken = signInForm.toAuthenticationObject();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue()
                .set("RT:"+authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return DefaultResponse.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenInfo);
    }
}

