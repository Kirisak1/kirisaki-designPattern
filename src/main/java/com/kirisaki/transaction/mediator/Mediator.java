package com.kirisaki.transaction.mediator;

import com.kirisaki.transaction.colleague.AbstractCustomer;
import com.kirisaki.transaction.colleague.Buyer;
import com.kirisaki.transaction.colleague.Payer;

/**
 * 具体中介者
 */
public class Mediator extends AbstractMediator {
    private AbstractCustomer buyer;
    private AbstractCustomer payer;

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    @Override
    public void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult) {
        if (customer instanceof Buyer) {
            System.out.println("朋友代付:" + buyer.getCustomerName() + "转发 OrderId" + orderId + "到用户" + targetCustomer + "进行支付");
        } else if (customer instanceof Payer) {
            System.out.println("代付完成:" + buyer.getCustomerName() + "完成 OrderId" + orderId + "的支付.通知" + targetCustomer + ",支付结果" + payResult);
        }
    }
}
