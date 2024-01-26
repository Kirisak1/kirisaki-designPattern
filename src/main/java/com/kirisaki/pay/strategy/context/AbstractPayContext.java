package com.kirisaki.pay.strategy.context;

import com.kirisaki.pojo.Order;

public abstract class AbstractPayContext {
    public abstract String execute(Order order);
}
