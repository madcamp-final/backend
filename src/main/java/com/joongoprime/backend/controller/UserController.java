package com.joongoprime.backend.controller;

import com.joongoprime.backend.entity.Users;
import com.joongoprime.backend.entity.form.SignIn;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import com.joongoprime.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/read")
    public DefaultResponse readUserInfo(@RequestParam("uid") String uid){
        Optional<Users> user = userService.read(uid);
        if (user.isPresent()) {
            Users foundUser = user.get();
            return DefaultResponse.res(StatusCode.OK, ResponseMessage.READ_USER, foundUser);
        }
        else {
            return DefaultResponse.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        }
    }

    @PostMapping("s/sign-in")
    public DefaultResponse userSignIn(@RequestBody SignIn userSignInForm) {
        return userService.userSignIn(userSignInForm);
    }

}
