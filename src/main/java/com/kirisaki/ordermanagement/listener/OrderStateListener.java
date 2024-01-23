package com.kirisaki.ordermanagement.listener;

import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Component
@WithStateMachine(name="orderStateMachine")
public class OrderStateListener {

}
