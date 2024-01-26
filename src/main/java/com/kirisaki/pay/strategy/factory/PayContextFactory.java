package com.kirisaki.pay.strategy.factory;

import com.kirisaki.pay.strategy.PayStrategyInterface;
import com.kirisaki.pay.strategy.context.PayContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 具体工厂类
 */
@Component
public class PayContextFactory extends AbstractPayContextFactory<PayContext> {
    //静态 与类一同加载,
    private static final Map<String, PayContext> payContexts = new ConcurrentHashMap<>();

    @Override
    public PayContext getContext(Integer payType) {
        //根据传入的参数来判断需要调用哪种支付方式
        StrategyEnum strategyEnum = payType == 1 ? StrategyEnum.alipay : payType == 2 ? StrategyEnum.wechat : null;
/*        switch (payType) {
            case 1 :
                strategyEnum = StrategyEnum.alipay;
                break;
            case 2 :
                strategyEnum = StrategyEnum.wechat;
                break;
            default:
                throw new UnsupportedOperationException("不支持的支付类型");
        }*/
        if (strategyEnum == null) {
            throw new UnsupportedOperationException("payType not supported");
        }
        //从集合中获取
        PayContext context = payContexts.get(strategyEnum.name());
        //如果不存在就新建对象然后放入到集合中
        if (context == null) {
            try {
                PayStrategyInterface payStrategy = (PayStrategyInterface) Class.forName(strategyEnum.getValue()).newInstance();
                PayContext payContext = new PayContext(payStrategy);
                payContexts.put(strategyEnum.name(), payContext);
            } catch (Exception e) {
                throw new UnsupportedOperationException("Get payStrategy failed!");
            }
        }
        return payContexts.get(strategyEnum.name());
    }
}
