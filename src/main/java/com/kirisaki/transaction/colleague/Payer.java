package com.kirisaki.transaction.colleague;

import com.kirisaki.transaction.mediator.AbstractMediator;

public class Payer extends AbstractCustomer {
    public Payer(String orderId, AbstractMediator mediator, String customerName) {
        super(orderId, mediator, customerName);
    }

    @Override
    public void messageTransfer(String orderId, String targetCustom, String payResult) {

    }
}
