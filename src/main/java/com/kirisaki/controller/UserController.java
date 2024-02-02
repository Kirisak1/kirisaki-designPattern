package com.kirisaki.controller;

import com.kirisaki.adapter.Login3rdTarget;
import com.kirisaki.pojo.BusinessLaunch;
import com.kirisaki.pojo.UserInfo;
import com.kirisaki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 第三方登录giteee
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/gitee")
    public String gitee(String code, String state) {
        return login3rdTarget.loginByGitee(code, state);
    }

    /**
     * 过滤业务投放数据
     *
     * @param city
     * @param sex
     * @param product
     * @return
     */
    @PostMapping("/business/launch")
    public List<BusinessLaunch> filterBusinessLaunch(@RequestParam("city") String city, @RequestParam("sex") String sex, @RequestParam("product") String product) {
        return userService.filterBusinessLaunch(city, sex, product);
    }
    @PostMapping("/ticket")
    public Object createTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
       return  userService.createTicket(type, productId, content, title, bankInfo, taxId);
    }
}
