package com.kirisaki.ordermanagement.audit;

import org.springframework.stereotype.Component;

@Component
public class SendOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        return auditLog;
    }
}
