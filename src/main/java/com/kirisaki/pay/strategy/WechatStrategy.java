package com.kirisaki.pay.strategy;

import com.kirisaki.pojo.Order;

/**
 * 微信支付, 不实现
 */
public class WechatStrategy implements PayStrategyInterface{
    @Override
    public String pay(Order order) {
        return null;
    }
}
