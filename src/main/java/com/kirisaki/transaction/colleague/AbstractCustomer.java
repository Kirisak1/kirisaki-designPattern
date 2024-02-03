package com.kirisaki.transaction.colleague;

import com.kirisaki.transaction.mediator.AbstractMediator;

/**
 * 抽象同事类
 */
public abstract class AbstractCustomer {
    public AbstractMediator mediator;
    public String orderId;
    public String customerName;


    AbstractCustomer(String orderId, AbstractMediator mediator, String customerName) {
        this.mediator = mediator;
        this.orderId = orderId;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public abstract void messageTransfer(String orderId, String targetCustom, String payResult);
}
