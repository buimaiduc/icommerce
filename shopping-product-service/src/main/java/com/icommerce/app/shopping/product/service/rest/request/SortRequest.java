package com.icommerce.app.shopping.product.service.rest.request;

public class SortRequest {
    private String sortField;
    private SortDirection sortDirection;

    public String getSortField() {
        return this.sortField;
    }

    public void setSortField(final String sortField) {
        this.sortField = sortField;
    }

    public SortDirection getSortDirection() {
        return this.sortDirection;
    }

    public void setSortDirection(final SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }
}
