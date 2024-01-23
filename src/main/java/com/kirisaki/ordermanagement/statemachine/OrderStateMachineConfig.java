package com.kirisaki.ordermanagement.statemachine;

import com.kirisaki.ordermanagement.state.OrderState;
import com.kirisaki.ordermanagement.state.OrderStateChangeAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.redis.RedisStateMachinePersister;

import java.util.EnumSet;

@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderState, OrderStateChangeAction> {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderStateChangeAction> states) throws Exception {
        states.withStates()
                //初始化订单状态
                .initial(OrderState.ORDER_WAIT_PAY)
                //将所有的状态都加载到状态机中
                .states(EnumSet.allOf(OrderState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderStateChangeAction> transitions) throws Exception {
        transitions
                //订单待支付到订单待发送的转换
                .withExternal()
                .source(OrderState.ORDER_WAIT_PAY)
                .target(OrderState.ORDER_WAIT_SEND)
                .event(OrderStateChangeAction.PAY_ORDER)
                .and()
                //订单待发送到订单待接收的转换
                .withExternal()
                .source(OrderState.ORDER_WAIT_SEND)
                .target(OrderState.ORDER_WAIT_RECEIVE)
                .event(OrderStateChangeAction.SEND_ORDER)
                .and()
                //订单待接收到订单完成的转换
                .withExternal()
                .source(OrderState.ORDER_WAIT_RECEIVE)
                .target(OrderState.ORDER_FINISH)
                .event(OrderStateChangeAction.RECEIVE_ORDER);
    }

    @Bean(name = "stateMachineRedisPersister")
    public RedisStateMachinePersister<OrderState, OrderStateChangeAction> gerRedisPersister() {

        RedisStateMachineContextRepository<OrderState, OrderStateChangeAction> repository = new RedisStateMachineContextRepository<>(redisConnectionFactory);
        RepositoryStateMachinePersist<OrderState, OrderStateChangeAction> persist = new RepositoryStateMachinePersist<>(repository);
        return new RedisStateMachinePersister<>(persist);
    }
}
