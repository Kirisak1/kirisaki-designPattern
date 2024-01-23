package com.kirisaki.bridge.abst;

import com.kirisaki.bridge.function.RegisterLoginFunInterface;
import com.kirisaki.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;


public class RegisterLoginComponent extends AbstractRegisterLoginComponent {
    public RegisterLoginComponent(RegisterLoginFunInterface funInterface) {
        super(funInterface);
    }

    @Override
    public String login(String account, String password) {
        return funInterface.login(account, password);
    }

    @Override
    public String register(UserInfo userInfo) {
        return funInterface.register(userInfo);
    }

    @Override
    public boolean checkUserExists(String userName) {
        return funInterface.checkUserExists(userName);
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        return funInterface.login3rd(request);
    }
}
