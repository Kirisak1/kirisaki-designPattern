package com.kirisaki.ticket.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业发票类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyTicket implements Cloneable {
    /**
     * 发票固定不变的信息
     */
    private String finalInfo;
    /**
     * 发票抬头
     */
    private String title;
    /**
     * 企业税号
     */
    private String taxId;
    /**
     * 银行卡信息
     */
    private String bankInfo;
    /**
     * 商品信息
     */
    private String product;
    /**
     * 税率丶发票代码丶校验码丶收款方等信息
     */
    private String content;

}
