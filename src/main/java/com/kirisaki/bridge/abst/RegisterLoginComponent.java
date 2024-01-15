package com.kirisaki.bridge.abst;

import com.kirisaki.bridge.function.RegisterLoginFunInterface;
import com.kirisaki.pojo.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

public class RegisterLoginComponent extends AbstractRegisterLoginComponent {
    protected RegisterLoginComponent(RegisterLoginFunInterface funInterface) {
        super(funInterface);
    }

    // todo 是否要改成public 不确定
    @Override
    protected String login(String account, String password) {
        return funInterface.login(account, password);
    }

    @Override
    protected String register(UserInfo userInfo) {
        return funInterface.register(userInfo);
    }

    @Override
    protected boolean checkUserExists(String userName) {
        return funInterface.checkUserExists(userName);
    }

    @Override
    protected String login3rd(HttpServletRequest request) {
        return funInterface.login3rd(request);
    }
}
