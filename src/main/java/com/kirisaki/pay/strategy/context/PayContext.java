package com.kirisaki.pay.strategy.context;

import com.kirisaki.pay.strategy.PayStrategyInterface;
import com.kirisaki.pojo.Order;

public class PayContext extends AbstractPayContext {
    private PayStrategyInterface payStrategy;

    public PayContext(PayStrategyInterface payStrategy) {
        this.payStrategy = payStrategy;
    }

    @Override
    public String execute(Order order) {
        return this.payStrategy.pay(order);
    }
}
