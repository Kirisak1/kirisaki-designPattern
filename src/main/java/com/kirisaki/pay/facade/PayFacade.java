package com.kirisaki.pay.facade;

import com.kirisaki.pay.strategy.context.PayContext;
import com.kirisaki.pay.strategy.factory.PayContextFactory;
import com.kirisaki.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private PayContextFactory contextFactory;

    public String pay(Order order, Integer payType) {
        // 每次访问都需要新建对象, 会有大量的GC, 需要用策略工厂模式进行优化
        PayContext context = contextFactory.getContext(payType);
        return context.execute(order);
    }
}
