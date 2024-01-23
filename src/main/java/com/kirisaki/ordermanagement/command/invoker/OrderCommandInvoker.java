package com.kirisaki.ordermanagement.command.invoker;

import com.kirisaki.ordermanagement.command.OrderCommandInterface;
import com.kirisaki.pojo.Order;

public class OrderCommandInvoker {
    public void invoke(OrderCommandInterface command, Order order) {
        command.execute(order);
    }
}

