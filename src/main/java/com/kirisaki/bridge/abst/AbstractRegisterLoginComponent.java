package com.kirisaki.bridge.abst;

import com.kirisaki.bridge.function.RegisterLoginFunInterface;
import com.kirisaki.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;


public abstract class AbstractRegisterLoginComponent {
    protected RegisterLoginFunInterface funInterface;
    public AbstractRegisterLoginComponent(RegisterLoginFunInterface funInterface) {
        //校验
        validate(funInterface);
        this.funInterface = funInterface;
    }

    protected final void validate(RegisterLoginFunInterface funInterface) {
        if(!(funInterface instanceof RegisterLoginFunInterface)){
            throw new UnsupportedOperationException("Unknow register /login function type!");
        }
    }

    public abstract String login(String account, String password);

    public abstract String register(UserInfo userInfo);

    public abstract boolean checkUserExists(String userName);

    public abstract String login3rd(HttpServletRequest request);
}
