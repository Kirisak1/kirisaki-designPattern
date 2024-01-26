package com.kirisaki.pay.facade;

import com.kirisaki.pojo.Order;
import org.springframework.stereotype.Component;

/**
 * 支付门面
 * 只负责调用子接口, 以及 调整参数
 */
@Component
public class PayFacade {
    /**
     * 支付接口
     *
     * @param order   订单
     * @param payType 调用具体的某个字系统
     * @return
     */
    public String pay(Order order, Integer payType) {
        return null;
    }
}
