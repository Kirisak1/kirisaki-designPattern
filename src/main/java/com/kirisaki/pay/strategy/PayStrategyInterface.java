package com.kirisaki.pay.strategy;

import com.kirisaki.pojo.Order;

/**
 * 策略类的父类接口
 * 所有的具体策略都需要实现这个类
 */
public interface PayStrategyInterface {
    String pay(Order order);
}
