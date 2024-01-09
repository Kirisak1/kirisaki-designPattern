package com.kirisaki.adapter;

import com.alibaba.fastjson.JSONObject;
import com.kirisaki.pojo.UserInfo;
import com.kirisaki.service.UserService;
import com.kirisaki.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Date;

// 交给ioc容器管理
@Component
public class Login3rdAdapter extends UserService implements Login3rdTarget {
    @Value("${gitee.state}")
    private String giteeState;
    @Value("${gitee.token.url}")
    private String giteeTokenUrl;
    @Value("${gitee.user.url}")
    private String giteeUserUrl;
    @Value("${gitee.user.prefix}")
    private String giteeUserPrefix;

    /**
     * gitee第三方登录
     *
     * @param code
     * @param state
     * @return
     */
    @Override
    public String loginByGitee(String code, String state) {
        //gitee回调后会发送code 和state
        //需要判断state是否正确
        if (!state.equals(giteeState)) {
            throw new UnsupportedOperationException("Invalid state");
        }
        //正确后根据code 去获取token
        String tokenUrl = giteeTokenUrl.concat(code);
        JSONObject tokenResponse = HttpClientUtils.execute(tokenUrl, HttpMethod.POST);
        String token = String.valueOf(tokenResponse.get("access_token"));
        //获取到token之后再获取用户的个人信息,
        String userUrl = giteeUserUrl.concat(token);
        JSONObject userInfo = HttpClientUtils.execute(userUrl, HttpMethod.POST);
        //然后通过个人信息来进行注册和登录
        String userName = giteeUserPrefix.concat(String.valueOf(userInfo.get("name")));
        String password = userName;
        return autoRegister3rdAndLogin(userName, password);
    }

    @Override
    public String loginByWechat(String... params) {
        return null;
    }

    @Override
    public String loginByQQ(String... params) {
        return null;
    }

    /**
     * 调用原有的登录和注册逻辑
     *
     * @param userName
     * @param password
     * @return
     */
    private String autoRegister3rdAndLogin(String userName, String password) {
        //查看是否已经注册过,  如果注册过直接登录
        if (this.checkUserExists(userName)) {
            return super.login(userName, password);
        }
        //如果没有注册过, 注册之后再登录
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUserPassword(password);
        userInfo.setCreateDate(new Date());
        super.register(userInfo);
        return super.login(userName, password);
    }

}
