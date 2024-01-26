package com.kirisaki.pay.strategy.factory;

/**
 * 抽象工厂类
 */
public abstract class AbstractPayContextFactory<T> {
    /**
     * 获取集合中的子对象
     * @param payType 类型
     * @return 返回对象
     * 使用泛型来增强扩展性
     */
    public abstract T getContext(Integer payType);
}
