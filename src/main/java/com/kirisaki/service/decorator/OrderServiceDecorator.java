package com.kirisaki.service.decorator;

import com.kirisaki.pojo.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceDecorator extends AbstractOrderServiceDecorator {

    @Override
    protected void updateScoreAndSendRedPaper(String productId, int serviceLevel, float price) {

    }

    /**
     * 将订单支付与积分更新合并
     *
     * @param orderId
     * @param serviceLevel
     * @param price
     * @return
     */
    public Order decoratorPay(String orderId, int serviceLevel, float price) {
        Order order = super.pay(orderId);
        try {
            this.updateScoreAndSendRedPaper(order.getProductId(), serviceLevel, price);
        } catch (Exception e) {
            //重试机制,此处积分更新不能影响支付主流程
        }
        return order;
    }
}
