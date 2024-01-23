package com.kirisaki.ordermanagement.command;

import com.kirisaki.pojo.Order;

/**
 * 抽象命令类
 */
public interface OrderCommandInterface {
    void execute(Order order);
}
