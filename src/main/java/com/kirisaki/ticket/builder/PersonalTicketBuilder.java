package com.kirisaki.ticket.builder;

import com.kirisaki.ticket.product.PersonalTicket;

public class PersonalTicketBuilder extends TicketBuilder<PersonalTicket> {
    //创建一个新的 personalTicket 对象
    private PersonalTicket personalTicket = new PersonalTicket();

    //对于 PersonalTicket,我们仅需要 setCommonInfo 即可
    @Override
    public void setCommonInfo(String title, String product, String content) {
        personalTicket.setTitle(title);
        personalTicket.setProduct(product);
        personalTicket.setContent(content);
    }

    //返回个人电子发票
    @Override
    public PersonalTicket buildTicket() {
        return personalTicket;
    }
}
