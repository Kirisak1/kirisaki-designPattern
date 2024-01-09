package com.kirisaki.controller;

import com.kirisaki.adapter.Login3rdTarget;
import com.kirisaki.pojo.UserInfo;
import com.kirisaki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private Login3rdTarget login3rdTarget;

    @PostMapping("/login")
    public String login(String account, String password) {
        return userService.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo) {
        return userService.register(userInfo);
    }

    /**
     *  第三方登录giteee
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/gitee")
    public String gitee(String code, String state){
        return login3rdTarget.loginByGitee(code, state);
    }
}
