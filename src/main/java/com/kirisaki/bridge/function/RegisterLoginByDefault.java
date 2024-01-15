package com.kirisaki.bridge.function;

import com.kirisaki.pojo.UserInfo;
import com.kirisaki.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterLoginByDefault extends AbstractRegisterLoginFunc implements RegisterLoginFunInterface {
    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(String account, String password) {
        return super.commonLogin(account, password, userRepository);
    }

    @Override
    public String register(UserInfo userInfo) {
        return super.commonRegister(userInfo, userRepository);
    }

    @Override
    public boolean checkUserExists(String userName) {
        return super.commonCheckUserExists(userName, userRepository);
    }
}
