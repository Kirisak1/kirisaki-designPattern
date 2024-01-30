package com.kirisaki.service.decorator;

import com.kirisaki.pojo.Order;
import com.kirisaki.service.inter.OrderServiceInterface;

/**
 * 抽象装饰对象
 */

public abstract class AbstractOrderServiceDecorator implements OrderServiceInterface {
    /**
     * 关联需要装饰的对象
     */
    private OrderServiceInterface orderServiceInterface;


    public void setOrderServiceInterface(OrderServiceInterface orderServiceInterface) {
        this.orderServiceInterface = orderServiceInterface;
    }

    @Override
    public Order createOrder(String productId) {
        return this.orderServiceInterface.createOrder(productId);
    }

    @Override
    public Order send(String orderId) {
        return this.orderServiceInterface.send(orderId);
    }

    @Override
    public Order receive(String orderId) {
        return this.orderServiceInterface.receive(orderId);
    }

    @Override
    public String getPayUrl(String orderId, Float price, Integer payType) {
        return this.orderServiceInterface.getPayUrl(orderId, price, payType);
    }

    @Override
    public Order pay(String orderId) {
        return this.orderServiceInterface.pay(orderId);
    }

    /**
     * 更新积分, 对特定商品要发送红包
     *
     * @param productId    商品Id, 查询这个商品是否需要发送红包
     * @param serviceLevel 服务降级等级, 0 正常,1 RabbitMQ异步, 2暂停
     * @param price        商品价格, 根据价格/100 来更新用户积分
     */
    protected abstract void updateScoreAndSendRedPaper(String productId, int serviceLevel, float price);
}
