package com.icommerce.app.shopping.product.service.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductListResponse {
    @JsonProperty("products")
    List<ProductResponse> productResponses;

    public List<ProductResponse> getProductResponses() {
        return productResponses;
    }

    public void setProductResponses(List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }
}
