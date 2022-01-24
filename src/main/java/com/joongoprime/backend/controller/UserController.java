package com.joongoprime.backend.controller;

import com.joongoprime.backend.entity.Users;
import com.joongoprime.backend.entity.form.Forms;
import com.joongoprime.backend.format.DefaultResponse;
import com.joongoprime.backend.format.ResponseMessage;
import com.joongoprime.backend.format.StatusCode;
import com.joongoprime.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
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
    public DefaultResponse readUserInfo(){
        return userService.userInfo();
    }

    @CrossOrigin(origins="http://127.0.0.1:5500")
    @GetMapping("/confirm-payments")
    public DefaultResponse confirmPayments(@RequestParam String imp_uid) throws JSONException {
        return userService.confirmPayments(imp_uid);
    }

    @GetMapping("/modify-points")
    public DefaultResponse modifyPoints(@RequestParam String uid, @RequestParam Integer value){
        return userService.modifyUserPoints(uid, value);
    }

    @PostMapping("/sign-up")
    public DefaultResponse userSignUp(@RequestBody Forms.SignUp userSignUpForm){
        return userService.userSignUp(userSignUpForm);
    }

    @PostMapping("/sign-in")
    public DefaultResponse userSignIn(@RequestBody Forms.SignIn userSignInForm) {
        return userService.userSignIn(userSignInForm);
    }

}
