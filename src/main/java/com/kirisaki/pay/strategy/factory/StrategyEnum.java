package com.kirisaki.pay.strategy.factory;

/**
 * 具体支付类的映射,
 * 获取到支付类的类路径
 */
public enum StrategyEnum {
    alipay("com.kirisaki.pay.strategy.AlipayStrategy"),
    wechat("com.kirisaki.pay.strategy.WechatStrategy");

    String value = "";
    StrategyEnum(String valueStr) {
        this.value = valueStr;
    }
    public  String getValue() {
        return this.value;
    }
}
