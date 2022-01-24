package com.joongoprime.backend.entity.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignIn {
    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    //signInForm의 데이터(아이디, 비밀번호)기반으로 authentication 객체로 변환. service의 signin 함수에서 참조함
    public UsernamePasswordAuthenticationToken toAuthenticationObject() {
        //principal(인증받으려는 사람), credentials 를 인자로 받음
        //인증객체 생성
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}