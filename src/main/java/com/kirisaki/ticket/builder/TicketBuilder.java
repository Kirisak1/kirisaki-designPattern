package com.kirisaki.ticket.builder;

/**
 * 发票的抽象创建者
 */
public abstract class TicketBuilder<T> {
    /**
     * 设置通用发票信息
     *
     * @param title
     * @param product
     * @param content
     */
    public abstract void setCommonInfo(String title, String product, String content);

    /**
     * 设置企业税号
     *
     * @param taxId
     */
    public void setTaxId(String taxId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 设置企业银行卡信息
     *
     * @param bankInfo
     */
    public void setBankInfo(String bankInfo) {
        throw new UnsupportedOperationException();
    }

    /**
     * 抽象建造方法
     *
     * @return
     */
    public abstract T buildTicket();
}
