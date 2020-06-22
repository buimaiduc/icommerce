package com.icommerce.app.shopping.product.service.rest.request;

public class SearchRequest extends SearchProductRequest {

    private FilterCriteriaRequest filterFields;

    private SortRequest sortRequest;

    public SearchRequest() {
        // Default constructor
    }

    public SearchRequest(FilterCriteriaRequest filterFields) {
        this.filterFields = filterFields;
    }

    public FilterCriteriaRequest getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(FilterCriteriaRequest filterFields) {
        this.filterFields = filterFields;
    }

    public SortRequest getSortRequest() {
        return sortRequest;
    }

    public void setSortRequest(SortRequest sortRequest) {
        this.sortRequest = sortRequest;
    }

}
