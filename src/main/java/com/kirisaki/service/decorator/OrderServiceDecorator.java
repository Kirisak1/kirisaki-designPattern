package com.kirisaki.service.decorator;

import com.kirisaki.pojo.Order;
import com.kirisaki.pojo.Products;
import com.kirisaki.repo.ProductsRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceDecorator extends AbstractOrderServiceDecorator {
    @Value("${delay.service.time}")
    private String delayServiceTime;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    protected void updateScoreAndSendRedPaper(String productId, int serviceLevel, float price) {
        //需要跟对入参进行服务降级,
        switch (serviceLevel) {
            //正常
            case 0:
                int score = Math.round(price) / 100;
                System.out.println("正常处理,为用户更新积分! score = " + score);
                Products product = productsRepository.findByProductId(productId);
                if (product != null && product.getSendRedBag() == 1) {
                    System.out.println("正常处理,为用户发放红包! productId =" + productId);
                }
                break;
            //服务降级
            case 1:
                MessageProperties properties = new MessageProperties();
                properties.setExpiration(delayServiceTime);
                Message msg = new Message(productId.getBytes(), properties);
                rabbitTemplate.send("normalExchange", "myRKey", msg);
                System.out.println("延迟处理,时间=" + delayServiceTime);
                break;
            case 2:
                System.out.println("暂停服务!");
                break;
            default:
                throw new UnsupportedOperationException("不支持的服务级别!");
        }
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
