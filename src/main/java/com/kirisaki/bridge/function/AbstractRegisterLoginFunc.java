package com.kirisaki.bridge.function;

import com.kirisaki.pojo.UserInfo;
import com.kirisaki.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

/**
 * 登录, 注册的抽象类
 * 解决了实现类需要重写公共代码的问题
 */
public abstract class AbstractRegisterLoginFunc implements RegisterLoginFunInterface {


    protected String commonLogin(String account, String password,UserRepository userRepository) {
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account, password);
        //匹配账号和密码失败,则返回错误信息
        if (userInfo == null) {
            return "account / password ERROR";
        }
        return "Login success";
    }

    protected String commonRegister(UserInfo userInfo,UserRepository userRepository) {
        //如果当前账号已经存在,拒绝注册.账号名称需要唯一
        if (commonCheckUserExists(userInfo.getUserName(),userRepository)) {
            throw new RuntimeException("User already registered.");
        }
        userInfo.setCreateDate(new Date());
        //sava 方法是 JPA 中已有的方法,无须额外自己实现
        userRepository.save(userInfo);
        return "Register Success";
    }

    public boolean commonCheckUserExists(String userName,UserRepository userRepository) {
        UserInfo user = userRepository.findByUserName(userName);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public String login(String account, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String register(UserInfo userInfo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkUserExists(String userName) {
        throw new UnsupportedOperationException();
    }
    @Override
    public String login3rd(HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }
}
