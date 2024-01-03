package com.kirisaki.adapter;

import com.kirisaki.service.UserService;
import org.springframework.stereotype.Component;
// 交给ioc容器管理
@Component
public class Login3rdAdapter extends UserService implements Login3rdTarget {

    @Override
    public String loginByGitee(String code, String state) {
        return null;
    }

    @Override
    public String loginByWechat(String... params) {
        return null;
    }

    @Override
    public String loginByQQ(String... params) {
        return null;
    }
}
