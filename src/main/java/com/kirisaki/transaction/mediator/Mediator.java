package com.kirisaki.transaction.mediator;

import com.kirisaki.transaction.colleague.AbstractCustomer;
import com.kirisaki.transaction.colleague.Buyer;
import com.kirisaki.transaction.colleague.Payer;

/**
 * 具体中介者
 */
public class Mediator extends AbstractMediator{
    private AbstractCustomer buyer;
    private AbstractCustomer payer;
    public void setBuyer(Buyer buyer){
        this.buyer =buyer;
    }
    public void setPayer(Payer payer){
        this.payer = payer;
    }
    @Override
    public void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult) {

    }
}
