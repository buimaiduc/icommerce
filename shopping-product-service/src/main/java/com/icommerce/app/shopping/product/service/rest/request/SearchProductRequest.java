package com.icommerce.app.shopping.product.service.rest.request;


import com.icommerce.app.shopping.common.rest.annotation.ValidInputNumber;

public class SearchProductRequest {
    private String searchValue;
    private int page;
    @ValidInputNumber
    private int size;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
