package com.icommerce.app.shopping.order.service.rest.response;

import java.util.List;

public class SearchProductResponse {

    private long totalItems;

    private long loadedItems;

    private List<ProductResponse> products;

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public long getLoadedItems() {
        return loadedItems;
    }

    public void setLoadedItems(long loadedItems) {
        this.loadedItems = loadedItems;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }
}