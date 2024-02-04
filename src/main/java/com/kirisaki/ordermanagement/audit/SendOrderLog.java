package com.kirisaki.ordermanagement.audit;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SendOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        HashMap<String, String> extraLog = new HashMap<>();
        extraLog.put("快递公司", "x 速递");
        extraLog.put("快递编号", "100100100");
        auditLog.setDetails(extraLog);
        return auditLog;
    }
}
