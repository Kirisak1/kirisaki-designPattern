package com.kirisaki.ticket.builder;

import com.kirisaki.ticket.product.PersonalTicket;

public class PersonalTicketBuilder extends TicketBuilder<PersonalTicket>{
    //对于 PersonalTicket,我们仅需要 setCommonInfo 即可
    @Override
    public void setCommonInfo(String title, String product, String content) {

    }
    //返回电子发票
    @Override
    public PersonalTicket buildTicket() {
        return null;
    }
}
