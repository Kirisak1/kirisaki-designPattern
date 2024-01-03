package com.kirisaki.service;

import com.kirisaki.pojo.UserInfo;
import com.kirisaki.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String login(String account, String password) {
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account,password);
        //匹配账号和密码失败,则返回错误信息
        if (userInfo == null) {
            return "account / password ERROR";
        }
        return "Login success";
    }

    public String register(UserInfo userInfo) {
        //如果当前账号已经存在,拒绝注册.账号名称需要唯一
        if (checkUserExists(userInfo.getUserName())) {
            throw new RuntimeException("User already registered.");
        }
        userInfo.setCreateDate(new Date());
        //sava 方法是 JPA 中已有的方法,无须额外自己实现
         userRepository.save(userInfo);
        return "Register Success";
    }

    //根据用户账号名称检查用户是否已注册
    public boolean checkUserExists(String userName) {
        UserInfo userInfo = userRepository.findByUserName(userName);
        if (userInfo == null) {
            return false;
        }
        return true;
    }
}
