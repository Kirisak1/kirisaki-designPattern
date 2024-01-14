package com.kirisaki.bridge.function;

import com.alibaba.fastjson.JSONObject;
import com.kirisaki.pojo.UserInfo;
import com.kirisaki.repo.UserRepository;
import com.kirisaki.utils.HttpClientUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class RegisterLoginByGitee implements RegisterLoginFunInterface{
    @Value("${gitee.state}")
    private String giteeState;
    @Value("${gitee.token.url}")
    private String giteeTokenUrl;
    @Value("${gitee.user.url}")
    private String giteeUserUrl;
    @Value("${gitee.user.prefix}")
    private String giteeUserPrefix;
    @Autowired
    private UserRepository userRepository;
    @Override
    public String login(String account, String password) {
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account,password);
        //匹配账号和密码失败,则返回错误信息
        if (userInfo == null) {
            return "account / password ERROR";
        }
        return "Login success";
    }

    @Override
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

    @Override
    public boolean checkUserExists(String userName) {
        UserInfo userInfo = userRepository.findByUserName(userName);
        if (userInfo == null) {
            return false;
        }
        return true;
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        String state = request.getHeader("state");
        String code = request.getHeader("code");
        if (state == null || state.isEmpty() || code == null || code.isEmpty()) {
            throw  new RuntimeException("Invalid request");
        }
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
            return login(userName, password);
        }
        //如果没有注册过, 注册之后再登录
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUserPassword(password);
        userInfo.setCreateDate(new Date());
        register(userInfo);
        return login(userName, password);
    }
}
