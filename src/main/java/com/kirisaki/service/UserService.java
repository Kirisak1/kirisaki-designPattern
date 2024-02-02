package com.kirisaki.service;

import com.kirisaki.dutychain.AbstractBusinessHandler;
import com.kirisaki.dutychain.CityHandler;
import com.kirisaki.dutychain.builder.HandlerEnum;
import com.kirisaki.pojo.BusinessLaunch;
import com.kirisaki.pojo.UserInfo;
import com.kirisaki.repo.BusinessLaunchRepository;
import com.kirisaki.repo.UserRepository;
import com.kirisaki.ticket.proxy.DirectorProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BusinessLaunchRepository businessLaunchRepository;
    @Value("${duty.chain}")
    private String handlerType;
    private String currentHandlerType;
    private AbstractBusinessHandler currentHandler;
    @Autowired
    private DirectorProxy directorProxy;

    public String login(String account, String password) {
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account, password);
        //匹配账号和密码失败,则返回错误信息
        if (userInfo == null) {
            return "account / password ERROR";
        }
        return "Login success";
    }

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

    //根据用户账号名称检查用户是否已注册
    public boolean checkUserExists(String userName) {
        UserInfo userInfo = userRepository.findByUserName(userName);
        if (userInfo == null) {
            return false;
        }
        return true;
    }

    /**
     * 过滤业务投放数据
     *
     * @param city
     * @param sex
     * @param product
     * @return
     */
    public List<BusinessLaunch> filterBusinessLaunch(String city, String sex, String product) {
        //读取所有的业务投放数据
        List<BusinessLaunch> launchList = businessLaunchRepository.findAll();

        return builder().processHandler(launchList, city, sex, product);
    }

    /**
     * 组装责任链
     *
     * @return
     */
    private AbstractBusinessHandler builder() {
        //判断dutyChain
        if (handlerType == null) {
            return null;
        }
        if (currentHandlerType == null) {
            this.currentHandlerType = this.handlerType;
        }
        //判断配置是否与现在的配置相等
        //如果相等返回当前的调用链条
        if (handlerType.equals(currentHandlerType) && currentHandler != null) {
            return currentHandler;
        } else {
            System.out.println("配置有修改或首次初始化,组装责任链条!!!");
            //如果不相等就创建新的调用链条
            synchronized (this) {
                try {
                    AbstractBusinessHandler dummyHeadHandler = new CityHandler();
                    //前置节点, 避免出现 NPE
                    AbstractBusinessHandler preHandler = dummyHeadHandler;
                    //根据 dutyChain 组装新的调用链
                    List<String> handlerTypeList = Arrays.asList(handlerType.split(","));
                    for (String handlerType : handlerTypeList) {
                        AbstractBusinessHandler handler = (AbstractBusinessHandler) Class.forName(HandlerEnum.valueOf(handlerType).getValue()).newInstance();
                        preHandler.nextHandler = handler;
                        preHandler = handler;
                    }
                    //给成员变量赋值
                    this.currentHandler = dummyHeadHandler.nextHandler;
                    this.currentHandlerType = this.handlerType;
                    return currentHandler;
                } catch (Exception e) {
                    throw new UnsupportedOperationException(e);
                }
            }
        }
    }

    public Object createTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
        return directorProxy.buildTicket(type, productId, content, title, bankInfo, taxId);
    }
}
