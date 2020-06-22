package com.icommerce.app.shopping.product.service.rest.response;

import java.util.List;

public class ProductPriceTrackingResponse {
    List<Double> prices;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }
}
