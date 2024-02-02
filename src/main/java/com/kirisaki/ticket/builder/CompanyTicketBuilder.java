package com.kirisaki.ticket.builder;

import com.kirisaki.ticket.product.CompanyTicket;

/**
 * 企业发票创建者
 */
public class CompanyTicketBuilder extends TicketBuilder<CompanyTicket> {
    // 创建一个新的 companyTicket 对象
    private CompanyTicket companyTicket = new CompanyTicket();

    //对于 CompanyTicket,我们需要 setCommonInfo
    @Override
    public void setCommonInfo(String title, String product, String content) {
        companyTicket.setTitle(title);
        companyTicket.setProduct(product);
        companyTicket.setContent(content);
    }

    //对于  CompanyTicket,我们需要 setTaxId
    @Override
    public void setTaxId(String taxId) {
        companyTicket.setTaxId(taxId);
    }

    //对于 CompanyTicket,我们需要 setBankInfo
    @Override
    public void setBankInfo(String bankInfo) {
        companyTicket.setBankInfo(bankInfo);
    }

    //返回企业电子发票
    @Override
    public CompanyTicket buildTicket() {
        return companyTicket;
    }
}
