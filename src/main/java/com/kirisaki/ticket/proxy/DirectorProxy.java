package com.kirisaki.ticket.proxy;

import com.kirisaki.ticket.director.AbstractDirector;
import com.kirisaki.ticket.director.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 指挥者代理类
 */
@Component
public class DirectorProxy extends AbstractDirector {
    //关联被代理对象
    @Autowired
    private Director director;

    @Override
    public Object buildTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
        //前置处理:通过 productId 获取商品信息
        String product = this.getProduct(productId);
        //前置处理:校验银行卡信息
        if (bankInfo != null && !validateBankInfo(bankInfo)) {
            return null;
        }
        //代理 director 类的 buildTicket 方法
        return director.buildTicket(type, product, content, title, bankInfo, taxId);
    }

    /**
     * 前置处理:通过 productId 获取商品信息
     *
     * @param productId
     * @return
     */
    private String getProduct(String productId) {
        return "根据 productId 获取商品信息";
    }

    /**
     * 前置处理:校验银行卡信息
     *
     * @param bankInfo
     * @return
     */
    private boolean validateBankInfo(String bankInfo) {
        System.out.println("银行卡校验逻辑!");
        return true;
    }
}
