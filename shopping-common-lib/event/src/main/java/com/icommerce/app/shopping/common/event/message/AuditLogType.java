package com.icommerce.app.shopping.common.event.message;

public enum AuditLogType {
    PRODUCT_SEARCH("search"),
    PRODUCT_FILTER("filter"),
    PRODUCT_VIEW("view");

    private String logType;

    AuditLogType(String logType) {
        this.logType = logType;
    }

    public String getLogType() {
        return logType;
    }
}
