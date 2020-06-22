package com.icommerce.app.shopping.order.service.service.api.proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductProxyList {
    @JsonProperty("products")
    List<ProductProxy> productProxies;

    public List<ProductProxy> getProductProxies() {
        return productProxies;
    }

    public void setProductProxies(List<ProductProxy> productProxies) {
        this.productProxies = productProxies;
    }
}
