package com.kirisaki.ordermanagement.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 审计日志对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAuditLog {
    private String account;
    private String action;
    private Date date;
    private String orderId;
    private Object details;
}
