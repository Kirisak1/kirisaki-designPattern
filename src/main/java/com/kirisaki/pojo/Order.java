package com.kirisaki.pojo;

import com.kirisaki.ordermanagement.state.OrderState;
import lombok.*;

/**
 * 订单实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private int orderId;
    private int productId;
    private OrderState orderState;
    //浮点型
    private Float price;
}
