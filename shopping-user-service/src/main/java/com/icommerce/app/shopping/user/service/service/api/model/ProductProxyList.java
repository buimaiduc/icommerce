package com.icommerce.app.shopping.user.service.service.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductProxyList {
    @JsonProperty("products")
    List<FacebookAccountResponse> productProxies;

    public List<FacebookAccountResponse> getProductProxies() {
        return productProxies;
    }

    public void setProductProxies(List<FacebookAccountResponse> productProxies) {
        this.productProxies = productProxies;
    }
}
