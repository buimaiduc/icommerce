package com.icommerce.app.shopping.product.service.service;

import com.icommerce.app.shopping.product.service.model.ProductPriceTracking;

import java.util.List;

public interface ProductPriceTrackingService {
    ProductPriceTracking insert(ProductPriceTracking productPriceTracking);

    List<ProductPriceTracking> findByProductId(Long productId);
}
