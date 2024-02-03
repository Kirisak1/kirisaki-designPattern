package com.kirisaki.transaction.mediator;

import com.kirisaki.transaction.colleague.AbstractCustomer;
import com.kirisaki.transaction.colleague.Buyer;
import com.kirisaki.transaction.colleague.Payer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 具体中介者
 */
@Component
public class Mediator extends AbstractMediator {
    public static Map<String, Map<String, AbstractCustomer>> customerInstances = new ConcurrentHashMap<>();

    @Override
    public void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult) {
        if (customer instanceof Buyer) {
            AbstractCustomer buyer = customerInstances.get(orderId).get("buyer");
            System.out.println("朋友代付:" + buyer.getCustomerName() + "转发 OrderId" + orderId + "到用户" + targetCustomer + "进行支付");
        } else if (customer instanceof Payer) {
            AbstractCustomer payer = customerInstances.get(orderId).get("payer");
            System.out.println("代付完成:" + payer.getCustomerName() + "完成 OrderId" + orderId + "的支付.通知" + targetCustomer + ",支付结果" + payResult);
            customerInstances.remove(orderId);
        }
    }
}
