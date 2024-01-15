package com.kirisaki.controller;

import com.kirisaki.pojo.UserInfo;
import com.kirisaki.service.UserBridgeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bridge")
public class UserBridgeController {
    @Autowired
    private UserBridgeService userBridgeService;

    @PostMapping("/login")
    public String login(String account, String password) {
        return userBridgeService.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo) {
        return userBridgeService.register(userInfo);
    }

    /**
     *  第三方登录giteee
     * @param request
     * @return
     */
    @GetMapping("/gitee")
    public String gitee(HttpServletRequest request){
        return userBridgeService.login3rd(request,"GITEE");
    }
}
