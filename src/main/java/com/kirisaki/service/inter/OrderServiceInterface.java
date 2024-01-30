package com.kirisaki.service.inter;

import com.kirisaki.pojo.Order;

/**
 * 订单服务接口
 */
public interface OrderServiceInterface {
    Order createOrder(String productId);

    Order pay(String orderId);

    Order send(String orderId);

    Order receive(String orderId);

    String getPayUrl(String orderId, Float price, Integer payType);
}
