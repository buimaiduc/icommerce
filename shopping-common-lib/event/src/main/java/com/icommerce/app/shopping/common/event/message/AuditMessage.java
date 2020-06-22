package com.icommerce.app.shopping.common.event.message;

import java.io.Serializable;

public class AuditMessage implements Serializable {
    private static final long serialVersionUID = 762817883206597927L;
    private String Id;
    private String createdAt;
    private Long userId;
    private AuditLogType auditLogType;
    private String detail;
    private int redeliveredCount;

    public AuditMessage() {
        // default constructor
    }

    public AuditMessage(String id, String createdAt, Long userId, AuditLogType auditLogType, String detail, int redeliveredCount) {
        Id = id;
        this.createdAt = createdAt;
        this.userId = userId;
        this.auditLogType = auditLogType;
        this.detail = detail;
        this.redeliveredCount = redeliveredCount;
    }

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

    public AuditLogType getAuditLogType() {
        return auditLogType;
    }

    public void setAuditLogType(AuditLogType auditLogType) {
        this.auditLogType = auditLogType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getRedeliveredCount() {
        return redeliveredCount;
    }

    public void setRedeliveredCount(int redeliveredCount) {
        this.redeliveredCount = redeliveredCount;
    }

    public AuditMessage clone() {
        return new AuditMessage(this.getId(), this.getCreatedAt(), this.getUserId(),
                this.getAuditLogType(), this.getDetail(), this.getRedeliveredCount());
    }
}

