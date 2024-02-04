package com.kirisaki.ordermanagement.audit;

import java.util.Date;

/**
 * 抽象模板方法
 */
public abstract class AbstractAuditLogProcessor {
    private final OrderAuditLog basicAuditLog(String account, String action, String orderId) {
        OrderAuditLog auditLog = new OrderAuditLog();
        auditLog.setAccount(account);
        auditLog.setAction(action);
        auditLog.setOrderId(orderId);
        auditLog.setDate(new Date());
        return auditLog;
    }

    protected abstract OrderAuditLog buildDetails(OrderAuditLog auditLog);

    public final OrderAuditLog createAuditLog(String account, String action, String orderId) {
        OrderAuditLog orderAuditLog = basicAuditLog(account, action, orderId);
        return buildDetails(orderAuditLog);
    }
}
