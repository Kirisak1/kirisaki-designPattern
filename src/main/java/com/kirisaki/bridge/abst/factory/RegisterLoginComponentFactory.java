package com.kirisaki.bridge.abst.factory;

import com.kirisaki.bridge.abst.AbstractRegisterLoginComponent;
import com.kirisaki.bridge.abst.RegisterLoginComponent;
import com.kirisaki.bridge.function.RegisterLoginFunInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 生成不同登录方式的对象
 */
public class RegisterLoginComponentFactory {
    /**
     * 放置组件的Map
     */
   private static  Map<String, AbstractRegisterLoginComponent> componentMap = new ConcurrentHashMap<>();
    /**
     * 放置登录注册具体实现的Map
     */
   public static Map<String, RegisterLoginFunInterface> funMap = new ConcurrentHashMap<>();
    /**
     * @param type 实现类对应的具体方式
     */
    public static AbstractRegisterLoginComponent getComponent(String type) {
        AbstractRegisterLoginComponent component = componentMap.get(type);
        if (component == null) {
            synchronized (componentMap) {
                if (component == null) {
                    component = new RegisterLoginComponent(funMap.get(type));
                    componentMap.put(type, component);
                }
            }
        }
        return component;
    }
}
