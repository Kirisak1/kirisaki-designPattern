package com.kirisaki.dutychain.builder;

/**
 * 映射责任链对象的包路径
 */
public enum HandlerEnum {
    city("com.kirisaki.dutychain.CityHandler"),
    sex("com.kirisaki.dutychain.SexHandler"),
    product("com.kirisaki.dutychain.ProductsHandler");
    String value = "";

    HandlerEnum(String value) {
        this.value = value;
    }
    public  String getValue() {
        return this.value;
    }
}
