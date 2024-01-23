package com.kirisaki.service;

import com.kirisaki.bridge.abst.AbstractRegisterLoginComponent;
import com.kirisaki.bridge.abst.factory.RegisterLoginComponentFactory;
import com.kirisaki.pojo.UserInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserBridgeService {
    public String login(String account, String password) {
        AbstractRegisterLoginComponent component = RegisterLoginComponentFactory.getComponent("Default");
        return component.login(account, password);
    }

    public String register(UserInfo userInfo) {
        AbstractRegisterLoginComponent component = RegisterLoginComponentFactory.getComponent("Default");
        return component.register(userInfo);
    }

    public String login3rd(HttpServletRequest request, String type) {
        AbstractRegisterLoginComponent component = RegisterLoginComponentFactory.getComponent(type);
        return component.login3rd(request);
    }

}
