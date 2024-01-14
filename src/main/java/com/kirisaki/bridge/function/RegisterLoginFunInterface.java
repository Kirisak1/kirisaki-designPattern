package com.kirisaki.bridge.function;

import com.kirisaki.pojo.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

public interface RegisterLoginFunInterface {
    String login(String account, String password);
    String register(UserInfo userInfo);

    boolean checkUserExists(String userName);

    String login3rd(HttpServletRequest request);

}
