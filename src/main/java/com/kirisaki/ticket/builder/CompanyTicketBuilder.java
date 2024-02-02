package com.kirisaki.ticket.builder;

import com.kirisaki.ticket.product.CompanyTicket;

public class CompanyTicketBuilder extends TicketBuilder<CompanyTicket> {
    //对于 CompanyTicket,我们需要 setCommonInfo
    @Override
    public void setCommonInfo(String title, String product, String content) {

    }
    //对于  CompanyTicket,我们需要 setTaxId
    @Override
    public void setTaxId(String taxId) {
    }
    //对于 CompanyTicket,我们需要 setBankInfo
    @Override
    public void setBankInfo(String bankInfo) {
    }
    //返回企业电子发票
    @Override
    public CompanyTicket buildTicket() {
        return null;
    }
}
