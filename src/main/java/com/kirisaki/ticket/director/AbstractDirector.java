package com.kirisaki.ticket.director;

/**
 * 抽象导演类
 */
public abstract class AbstractDirector {
    public abstract Object buildTicket(String type, String product, String content, String title, String bankInfo, String taxId);
}
