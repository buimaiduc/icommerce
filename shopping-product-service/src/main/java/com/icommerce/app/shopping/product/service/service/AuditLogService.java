package com.icommerce.app.shopping.product.service.service;

import com.icommerce.app.shopping.common.event.message.AuditLogType;

public interface AuditLogService {

    void log(Long userId, Long productId, AuditLogType auditLogType);

    void log(Long userId, String detail, AuditLogType auditLogType);
}
