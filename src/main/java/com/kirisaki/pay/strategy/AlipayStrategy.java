package com.kirisaki.pay.strategy;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.kirisaki.pojo.Order;
import com.kirisaki.utils.Constants;

/**
 * 支付宝支付
 */
public class AlipayStrategy implements PayStrategyInterface {
    // 建议直接抄官方文档
    @Override
    public String pay(Order order) {
        AlipayClient alipayClient = new DefaultAlipayClient(Constants.ALIPAY_GATEWAY, Constants.APP_ID, Constants.APP_PRIVATE_KEY, "JSON", "UTF-8", Constants.ALIPAY_PUBLIC_KEY, Constants.SIGN_TYPE);
        AlipayTradeWapPayRequest payRequest = new AlipayTradeWapPayRequest();

        payRequest.setReturnUrl(Constants.CALLBACK_URL);

        payRequest.setBizContent("{\"out_trade_no\": \"" + order.getOrderId() + "\"," +
                "\"total_amount\":\"" + order.getPrice() + "\"," +
                "\"subject\":\"" + "Kirisaki" + "\"," +
                "\"body\":\"" + "商品描述" + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        try {
            String result = alipayClient.pageExecute(payRequest, "GET").getBody();
            return result;
        } catch (AlipayApiException e) {
            throw new UnsupportedOperationException("Alipay failed" + e);
        }
    }
}
