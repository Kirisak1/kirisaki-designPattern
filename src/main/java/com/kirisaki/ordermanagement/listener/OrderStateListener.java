package com.kirisaki.ordermanagement.listener;


import com.kirisaki.ordermanagement.command.OrderCommand;
import com.kirisaki.ordermanagement.command.invoker.OrderCommandInvoker;
import com.kirisaki.ordermanagement.state.OrderState;
import com.kirisaki.ordermanagement.state.OrderStateChangeAction;
import com.kirisaki.pojo.Order;
import com.kirisaki.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Component
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListener {
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;
    @Autowired
    private OrderCommand orderCommand;

    /**
     * 订单支付成功,修改order状态 并且将redis中的数据更新
     *
     * @param message
     * @return
     */
    @OnTransition(source = "ORDER_WAIT_PAY", target = "ORDER_WAIT_SEND")
    public boolean payToSend(Message<OrderStateChangeAction> message) {
        Order order = (Order) message.getHeaders().get("order");
        if (order.getOrderState() != OrderState.ORDER_WAIT_PAY) {
            throw new UnsupportedOperationException("order state error !");
        }
        order.setOrderState(OrderState.ORDER_WAIT_SEND);
        redisCommonProcessor.set(order.getOrderId(), order);
        //命令模式引用
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return true;
    }

    /**
     * 发送订单,修改order状态 并且将redis中的数据更新
     *
     * @param message
     * @return
     */
    @OnTransition(source = "ORDER_WAIT_SEND", target = "ORDER_WAIT_RECEIVE")
    public boolean sendToReceive(Message<OrderStateChangeAction> message) {
        Order order = (Order) message.getHeaders().get("order");
        if (order.getOrderState() != OrderState.ORDER_WAIT_SEND) {
            throw new UnsupportedOperationException("order state error !");
        }
        order.setOrderState(OrderState.ORDER_WAIT_RECEIVE);
        redisCommonProcessor.set(order.getOrderId(), order);
        //命令模式引用
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return true;
    }

    /**
     * 接收订单,修改order状态 并且将redis中的数据更新
     *
     * @param message
     * @return
     */
    @OnTransition(source = "ORDER_WAIT_RECEIVE", target = "ORDER_FINISH")
    public boolean receiveToFinish(Message<OrderStateChangeAction> message) {
        Order order = (Order) message.getHeaders().get("order");
        if (order.getOrderState() != OrderState.ORDER_WAIT_RECEIVE) {
            throw new UnsupportedOperationException("order state error !");
        }
        order.setOrderState(OrderState.ORDER_FINISH);
        redisCommonProcessor.remove(order.getOrderId());
        //命令模式引用
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return true;
    }
}
