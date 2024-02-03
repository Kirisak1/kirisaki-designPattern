package com.kirisaki.transaction.colleague;

import com.kirisaki.transaction.mediator.AbstractMediator;

public class Buyer extends AbstractCustomer {
    public Buyer(String orderId, AbstractMediator mediator, String customerName) {
        super(orderId, mediator, customerName);
    }

    @Override
    public void messageTransfer(String orderId, String targetCustomer, String payResult) {
        super.mediator.messageTransfer(orderId, targetCustomer, this, payResult);
    }
}
