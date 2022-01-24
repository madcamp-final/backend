package com.joongoprime.backend.service;

import com.joongoprime.backend.entity.Users;
import com.joongoprime.backend.entity.UsersRepository;
import com.joongoprime.backend.entity.form.Forms;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import com.joongoprime.backend.jwt.JwtTokenProvider;
import com.joongoprime.backend.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Users create(Users user){
        return usersRepository.save(user);
    }

    public Optional<Users> read(String uid) {
        return usersRepository.findById(uid);
    }

    public DefaultResponse userSignUp(@Validated Forms.SignUp signUpForm){
        try {
            Users user = Users.builder()
                    .uid(signUpForm.getEmail())
                    .user_name(signUpForm.getUser_name())
                    .password(passwordEncoder.encode(signUpForm.getPassword()))
                    .account_type("LOCAL")
                    .points(0)
                    .build();
            usersRepository.save(user);
        } catch (Exception e){
            e.printStackTrace();
            return DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_EXISTS);
        }
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.CREATED_USER);
    }

    public DefaultResponse userSignIn(@Validated Forms.SignIn signInForm){

        UsernamePasswordAuthenticationToken authenticationToken = signInForm.toAuthenticationObject();

        logger.info(authenticationToken.toString());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        logger.info(authentication.toString());

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue()
                .set("RT:"+authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return DefaultResponse.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenInfo);
    }

    public DefaultResponse userInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return DefaultResponse.res(StatusCode.BAD_REQUEST, ResponseMessage.TOKEN_FAILED);
        }
        if (authentication.getName().equals("anonymousUser")){
            return DefaultResponse.res(StatusCode.NEED_REFRESH, ResponseMessage.REQUIRES_TOKEN_UPDATE);
        }
        Optional users = usersRepository.findById(authentication.getName());
        if (!users.isPresent()) {
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        }
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.READ_USER, users.get());
    }

    public DefaultResponse confirmPayments(String imp_uid) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject httpBody = new JSONObject();
        httpBody.put("imp_key", "3115583846018864");
        httpBody.put("imp_secret", "27f81a91d27ecaa1729d5b9def455c04b0b982ce17cc001b56acd5d0542e8e59f3be4b0a578d9efa");
        try{
            HttpEntity<JSONObject> entity = new HttpEntity<>(httpBody, httpHeaders);
            ResponseEntity<JSONObject> token = restTemplate.postForEntity("https://api.iamport.kr/users/getToken", entity, JSONObject.class);
//            return token;
            return DefaultResponse.res(StatusCode.OK, ResponseMessage.RESULT_FOUND, token);
        } catch (Exception e){
            e.printStackTrace();
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.RESULT_NON_FOUND);
        } finally {
            httpHeaders.clear();
            httpBody.clear();
        }
    }

    public DefaultResponse modifyUserPoints(String uid, int value) {
        Optional<Users> users = usersRepository.findById(uid);
        if (!users.isPresent()){
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        }
        Users user = users.get();
        int currentPoints = user.setPoints(value);
        usersRepository.save(user);
        return DefaultResponse.res(StatusCode.OK, ResponseMessage.MODIFIED_POINTS(currentPoints), user);
    }
}

