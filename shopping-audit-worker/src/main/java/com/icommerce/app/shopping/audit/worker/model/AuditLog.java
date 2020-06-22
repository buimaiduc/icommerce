package com.icommerce.app.shopping.audit.worker.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("AuditLogs")
public class AuditLog {
    private String Id;
    private String createdAt;
    private Long userId;
    private String auditLogType;
    private String detail;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuditLogType() {
        return auditLogType;
    }

    public void setAuditLogType(String auditLogType) {
        this.auditLogType = auditLogType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
