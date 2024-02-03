package com.kirisaki.transaction.mediator;

import com.kirisaki.transaction.colleague.AbstractCustomer;

/**
 * 抽象的中介者
 */
public abstract class AbstractMediator {
    /**
     * 中转方法
     * @param orderId 订单ID
     * @param targetCustomer 目标对象
     * @param customer 请求发起的对象
     * @param payResult 支付结果 ,如果是代付则为null
     */
    public abstract void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult);
}
