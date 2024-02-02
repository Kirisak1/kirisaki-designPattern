package com.kirisaki.ticket.director;

import org.springframework.stereotype.Component;

/**
 * 具体导演类
 */
@Component
public class Director extends AbstractDirector {
    @Override
    public Object buildTicket(String type, String product, String content, String title, String bankInfo, String taxId) {
        return null;
    }
}
