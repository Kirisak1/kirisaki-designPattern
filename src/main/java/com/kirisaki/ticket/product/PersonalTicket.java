package com.kirisaki.ticket.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 个人发票类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalTicket implements Cloneable {
    /**
     * 发票固定不变的信息
     */
    private String finalInfo;
    /**
     * 发票抬头
     */
    private String title;
    /**
     * 商品信息
     */
    private String product;
    /**
     * 税率丶发票代码丶校验码丶收款方等信息
     */
    private String content;

    @Override
    public PersonalTicket clone() {
        PersonalTicket personalTicket = null;
        try {
            personalTicket = (PersonalTicket) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return personalTicket;
    }
}
