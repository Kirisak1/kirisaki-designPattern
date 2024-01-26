package com.kirisaki.pay.facade;

import com.kirisaki.pay.strategy.AlipayStrategy;
import com.kirisaki.pay.strategy.WechatStrategy;
import com.kirisaki.pay.strategy.context.PayContext;
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
        // 每次访问都需要新建对象, 会有大量的GC, 需要用策略工厂模式进行优化

        switch (payType) {
            case 1:
                AlipayStrategy alipayStrategy = new AlipayStrategy();
                PayContext aliPayContext = new PayContext(alipayStrategy);
                return aliPayContext.execute(order);
            case 2:
                WechatStrategy wechatStrategy = new WechatStrategy();
                PayContext wechatPayContext = new PayContext(wechatStrategy);
                return wechatPayContext.execute(order);
            default:
                throw new UnsupportedOperationException("PayType not supported");
        }
    }
}
