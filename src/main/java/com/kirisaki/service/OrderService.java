package com.kirisaki.service;

import com.kirisaki.ordermanagement.command.OrderCommand;
import com.kirisaki.ordermanagement.command.invoker.OrderCommandInvoker;
import com.kirisaki.ordermanagement.state.OrderState;
import com.kirisaki.ordermanagement.state.OrderStateChangeAction;
import com.kirisaki.pay.facade.PayFacade;
import com.kirisaki.pojo.Order;
import com.kirisaki.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.redis.RedisStateMachinePersister;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private StateMachine<OrderState, OrderStateChangeAction> orderStateMachine;
    @Autowired
    private RedisStateMachinePersister<OrderState, OrderStateChangeAction> stateMachineRedisPersister;
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;
    @Autowired
    private OrderCommand orderCommand;
    @Autowired
    private PayFacade payFacade;

    /**
     * 创建订单
     *
     * @param productId
     * @return
     */
    public Order createOrder(String productId) {
        String orderId = "OID" + productId;
        Order order = Order.builder().orderId(orderId).productId(productId).orderState(OrderState.ORDER_WAIT_PAY).build();

        redisCommonProcessor.set(order.getOrderId(), order, 900);
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return order;
    }

    /**
     * 订单支付
     *
     * @param orderId
     * @return
     */
    public Order pay(String orderId) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        Message<OrderStateChangeAction> message = MessageBuilder.withPayload(OrderStateChangeAction.PAY_ORDER).setHeader("order", order).build();
        if (changeStateAction(message, order)) {
            return order;
        }
        return null;

    }

    /**
     * 发送订单
     *
     * @param orderId
     * @return
     */
    public Order send(String orderId) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        Message<OrderStateChangeAction> message = MessageBuilder.withPayload(OrderStateChangeAction.SEND_ORDER).setHeader("order", order).build();
        if (changeStateAction(message, order)) {
            return order;
        }
        return null;
    }

    /**
     * 接受订单
     *
     * @param orderId
     * @return
     */
    public Order receive(String orderId) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        Message<OrderStateChangeAction> message = MessageBuilder.withPayload(OrderStateChangeAction.RECEIVE_ORDER).setHeader("order", order).build();
        if (changeStateAction(message, order)) {
            return order;
        }
        return null;
    }

    /**
     * 状态机的相关操作
     *
     * @param message
     * @param order
     * @return
     */
    private boolean changeStateAction(Message<OrderStateChangeAction> message, Order order) {
        try {
            //启动状态机
            orderStateMachine.start();
            stateMachineRedisPersister.restore(orderStateMachine, order.getOrderId() + "STATE");
            boolean res = orderStateMachine.sendEvent(message);
            stateMachineRedisPersister.persist(orderStateMachine, order.getOrderId() + "STATE");
            if (message.getPayload().name().equals(OrderStateChangeAction.RECEIVE_ORDER.name())) {
                redisCommonProcessor.remove(order.getOrderId() + "STATE");
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return false;
    }

    /**
     * 调用门面模式, 返回支付地址
     *
     * @param orderId
     * @param price
     * @param payType
     * @return
     */
    public String getPayUrl(String orderId, Float price, Integer payType) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        order.setPrice(price);
        return payFacade.pay(order, payType);
    }
}
