package com.kirisaki.pay.strategy.factory;

import com.kirisaki.pay.strategy.context.PayContext;
import org.springframework.stereotype.Component;

/**
 * 具体工厂类
 */
@Component
public class PayContextFactory extends AbstractPayContextFactory<PayContext>{
    @Override
    public PayContext getContext(Integer payType) {
        return null;
    }
}
